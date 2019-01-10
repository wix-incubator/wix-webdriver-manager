package com.wixpress.automation.webdriver.enums;

public enum CloudService {
    SAUCE_LABS,
    BROWSER_STACK;

    /**
     * Returns cloud type according to given value
     *
     * @param cloudService raw value
     * @return web driver strategy type
     */
    public static CloudService fromString(String cloudService) {
        try {
            return valueOf(cloudService.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("cloud service '%s' is not supported", cloudService), e);
        }
    }
}
