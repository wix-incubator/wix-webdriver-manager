package com.wixpress.automation.webdriver.enums;

import com.google.common.base.Enums;

public enum RunOn {
    /**
     * 'Unknown' value - to determine illegal environment
     */
    UNKNOWN,

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
     * Returns web driver strategy type according to given value
     *
     * @param value raw value
     * @return run on type
     */
    public static RunOn fromString(String value) {
        return Enums.getIfPresent(RunOn.class, value.toUpperCase()).or(UNKNOWN);
    }
}
