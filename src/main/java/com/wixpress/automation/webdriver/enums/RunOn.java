package com.wixpress.automation.webdriver.enums;

public enum RunOn {
    /**
     * For running new web driver on local machine
     */
    LOCAL,

    /**
     * For running new web driver on agent in grid
     */
    GRID,

    /**
     * For running new webdriver on remote machine
     */
    REMOTE,

    /**
     * For running web driver in cloud service
     */
    CLOUD;

    /**
     * Returns run on option according to given value
     *
     * @param value raw value
     * @return web driver strategy type
     */
    public static RunOn fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("Run option '%s' is not supported", value), e);
        }
    }
}
