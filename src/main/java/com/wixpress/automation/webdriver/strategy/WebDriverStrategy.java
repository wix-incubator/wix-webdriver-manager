package com.wixpress.automation.webdriver.strategy;

import org.openqa.selenium.WebDriver;

public interface WebDriverStrategy {
    /**
     * Returns next available web driver instance
     *
     * @return web driver
     */
    WebDriver get();

    /**
     * Releases given web driver
     *
     * @param webDriver web driver to release
     */
    void release(WebDriver webDriver);

    /**
     * Recovers given web driver and returns a new one
     *
     * @param webDriver web driver to recover
     * */
    WebDriver recover(WebDriver webDriver);

    /**
     * Destroys all web drivers
     */
    default void destroy() {}
}
