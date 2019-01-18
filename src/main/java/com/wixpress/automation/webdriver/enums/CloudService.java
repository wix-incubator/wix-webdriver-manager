package com.wixpress.automation.webdriver.enums;

import com.google.common.base.Enums;

public enum CloudService {
    /**
     * 'Unknown' value - to determine illegal strategy type
     */
    UNKNOWN,

    /**
     * To run tests on SauceLabs service
     */
    SAUCE_LABS,

    /**
     * To run tests on BrowserStack service
     */
    BROWSER_STACK;

    /**
     * Returns web driver strategy type according to given value
     *
     * @param cloudService raw value
     * @return cloud service type
     */
    public static CloudService fromString(String cloudService) {
        return Enums.getIfPresent(CloudService.class, cloudService.toUpperCase()).or(UNKNOWN);
    }
}
