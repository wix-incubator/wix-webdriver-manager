package com.wixpress.automation.webdriver.factory;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import com.wixpress.automation.webdriver.enums.WebDriverType;
import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {
    /**
     * Creates a web driver according to given browser type
     *
     * @param webDriverType web driver type
     * @return web driver
     */
    WebDriver create(WebDriverType webDriverType);

    /**
     * Creates a web driver according to browser type from configuration build parameters
     *
     * @return web driver
     */
    default WebDriver create() {
        WebDriverType webDriverType = WebDriverType.fromString(WebDriverConfig.getInstance().getWebDriverType());
        return create(webDriverType);
    }
}
