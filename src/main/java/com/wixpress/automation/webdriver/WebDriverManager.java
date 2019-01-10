package com.wixpress.automation.webdriver;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import com.wixpress.automation.webdriver.enums.RunOn;
import com.wixpress.automation.webdriver.exceptions.WebDriverManagerException;
import com.wixpress.automation.webdriver.factory.CloudWebDriverFactory;
import com.wixpress.automation.webdriver.factory.LocalWebDriverFactory;
import com.wixpress.automation.webdriver.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class WebDriverManager {


    public WebDriverManager(WebDriverConfig webDriverConfig) {
        WebDriverConfig.apply(webDriverConfig);
    }

    public WebDriver create() {
        return createFactory().create();
    }

    public void destroy(WebDriver webDriver) {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private WebDriverFactory createFactory() {
        switch (runOn()) {
            case LOCAL:
                return new LocalWebDriverFactory();
            case GRID:
                throw new WebDriverManagerException("Not implemented yet");
            case REMOTE:
                throw new WebDriverManagerException("Not implemented yet");
            case CLOUD:
                return new CloudWebDriverFactory();
            default:
                throw new WebDriverManagerException("Failed to create web driver factory with type: " + runOn());
        }
    }

    private RunOn runOn() {
        return RunOn.fromString(WebDriverConfig.getInstance().getRunOn());
    }
}
