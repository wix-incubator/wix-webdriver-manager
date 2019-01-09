package com.wixpress.automation.webdriver.capabilities;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PlatformCapabilities {

    public static DesiredCapabilities defaultCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "");
        capabilities.setCapability("seleniumVersion", "");
        capabilities.setCapability("screenResolution", "");
        capabilities.setCapability("record-screenshots", true);
        capabilities.setCapability("webdriver.remote.quietExceptions", true);


        return capabilities;
    }

    public static DesiredCapabilities iOSCapabilities() {
        DesiredCapabilities capabilities = mobileCapabilities();
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
        capabilities.setCapability(IOSMobileCapabilityType.WAIT_FOR_APP_SCRIPT, "true");
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, "true");
        capabilities.setCapability(IOSMobileCapabilityType.NATIVE_WEB_TAP, "true");

        return capabilities;
    }

    public static DesiredCapabilities androidCapabilities() {
        DesiredCapabilities capabilities = mobileCapabilities();
        capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");

        return capabilities;
    }

    private static DesiredCapabilities mobileCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, "true");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "0");

        return capabilities;
    }
}
