package com.wixpress.automation.webdriver;

import com.wixpress.automation.webdriver.binaries.WebDriverBinaryManager;
import com.wixpress.automation.webdriver.capabilities.BrowserCapabilities;
import com.wixpress.automation.webdriver.capabilities.PlatformCapabilities;
import com.wixpress.automation.webdriver.enums.WebDriverType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverManager {

    public WebDriver create(WebDriverType webDriverType) {
        WebDriver webDriver;

        WebDriverBinaryManager.install(webDriverType);

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
            case IOS:
                webDriver = new IOSDriver(getLocalServerURL(), PlatformCapabilities.iOSCapabilities());
                break;
            case ANDROID:
                webDriver = new AndroidDriver(getLocalServerURL(), PlatformCapabilities.androidCapabilities());
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + webDriverType);
        }

        return webDriver;
    }

    private URL getLocalServerURL() {
        try {
            return new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Server URL instance was not created", e);
        }
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
