package com.wixpress.automation.webdriver;

import com.wixpress.automation.webdriver.capabilities.BrowserCapabilities;
import com.wixpress.automation.webdriver.enums.WebDriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;

public class WebDriverManager {

    public WebDriver create(WebDriverType webDriverType) {
        WebDriver webDriver;

        switch (webDriverType) {
            case CHROME:
                webDriver = new ChromeDriver(BrowserCapabilities.getChromeOptions());
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver(BrowserCapabilities.getFirefoxOptions());
                break;
            case EXPLORER:
                webDriver = new InternetExplorerDriver(BrowserCapabilities.getExplorerOptions());
                break;
            case EDGE:
                webDriver = new EdgeDriver(createEdgeDriverService(), BrowserCapabilities.getEdgeOptions());
                break;
            case SAFARI:
                webDriver = new SafariDriver(BrowserCapabilities.getSafariOptions());
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + webDriverType);
        }

        return webDriver;
    }

    private EdgeDriverService createEdgeDriverService() {
        EdgeDriverService edgeDriverService = new EdgeDriverService.Builder()
                .usingAnyFreePort()
                .build();

        try {
            edgeDriverService.start();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to start Edge Driver Service with error: %s",
                    e.getMessage()));
        }

        return edgeDriverService;
    }
}
