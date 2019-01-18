package com.wixpress.automation.webdriver.enums;

import com.google.common.base.Enums;

public enum WebDriverType {
    /**
     * 'Unknown' value - to determine illegal environment
     */
    UNKNOWN,

    /**
     * Run Google Chrome
     */
    CHROME,

    /**
     * Run Mozilla Firefox
     */
    FIREFOX,

    /**
     * Run MS Internet Explorer
     */
    EXPLORER,

    /**
     * Run MS Edge
     */
    EDGE,

    /**
     * Run Apple Safari
     */
    SAFARI,

    /**
     * Use
     */
    IOS,

    /**
     * 'Unknown' value - to determine illegal environment
     */
    ANDROID;

    /**
     * Returns web driver strategy type according to given value
     *
     * @param value raw value
     * @return web driver type
     */
    public static WebDriverType fromString(String value) {
        return Enums.getIfPresent(WebDriverType.class, value.toUpperCase()).or(UNKNOWN);
    }

    public boolean isMobile() {
        return equals(ANDROID) || equals(IOS);
    }
}
