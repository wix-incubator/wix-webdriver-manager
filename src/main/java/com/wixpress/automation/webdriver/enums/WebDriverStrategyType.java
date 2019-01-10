package com.wixpress.automation.webdriver.enums;

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
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
