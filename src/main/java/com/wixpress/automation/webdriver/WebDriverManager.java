package com.wixpress.automation.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverManager {

    public WebDriver create(WebDriverType webDriverType) {
        WebDriver webDriver;

        switch (webDriverType) {
            case CHROME:
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
            case EXPLORER:
                webDriver = new InternetExplorerDriver();
                break;
            case EDGE:
                webDriver = new EdgeDriver();
                break;
            case SAFARI:
                webDriver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + webDriverType);
        }

        return webDriver;
    }
}
