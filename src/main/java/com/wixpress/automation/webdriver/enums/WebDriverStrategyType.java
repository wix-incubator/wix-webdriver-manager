package com.wixpress.automation.webdriver.enums;

import com.google.common.base.Enums;

public enum WebDriverStrategyType {
    /**
     * 'Unknown' value - to determine illegal strategy type
     */
    UNKNOWN,

    /**
     * For creating new web driver instances each test
     */
    NEW_INSTANCE,

    /**
     * For recycling web drivers between tests
     */
    RECYCLED;

    /**
     * Returns web driver strategy type according to given value
     *
     * @param value raw value
     * @return web driver strategy type
     */
    public static WebDriverStrategyType fromString(String value) {
       return Enums.getIfPresent(WebDriverStrategyType.class, value.toString()).or(UNKNOWN);
    }
}
