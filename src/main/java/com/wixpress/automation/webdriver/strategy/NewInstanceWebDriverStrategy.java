package com.wixpress.automation.webdriver.strategy;

import com.wixpress.automation.webdriver.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class NewInstanceWebDriverStrategy implements WebDriverStrategy {

    private final WebDriverFactory webDriverFactory;

    public NewInstanceWebDriverStrategy(WebDriverFactory webDriverFactory) {
        this.webDriverFactory = webDriverFactory;
    }

    @Override
    public WebDriver get() {
        return webDriverFactory.create();
    }

    @Override
    public void release(WebDriver webDriver) {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Override
    public WebDriver recover(WebDriver webDriver) {
        throw new UnsupportedOperationException("Can't recover web driver for New Instance Strategy");
    }
}
