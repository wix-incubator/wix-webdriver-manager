package com.wixpress.automation.webdriver.capabilities;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PlatformCapabilities {

    private static final String BUILD_NAME = WebDriverConfig.getInstance().getBuildName();
    private static final String BUILD_NUMBER = WebDriverConfig.getInstance().getBuildNumber();

    private static final String PLATFORM = WebDriverConfig.getInstance().getPlatform();
    private static final String SELENIUM_VERSION = WebDriverConfig.getInstance().getSeleniumVersion();
    private static final String SCREEN_RESOLUTION = WebDriverConfig.getInstance().getScreenResolution();

    private static final String APPIUM_VERSION = WebDriverConfig.getInstance().getAppiumVersion();

    public static DesiredCapabilities defaultCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", PLATFORM);
        capabilities.setCapability("seleniumVersion", SELENIUM_VERSION);
        capabilities.setCapability("screenResolution", SCREEN_RESOLUTION);
        capabilities.setCapability("record-screenshots", true);
        capabilities.setCapability("webdriver.remote.quietExceptions", true);
        capabilities.setCapability("name", BUILD_NAME);
        capabilities.setCapability("build", BUILD_NUMBER);

        return capabilities;
    }

    public static DesiredCapabilities iOSCapabilities() {
        DesiredCapabilities capabilities = mobileCapabilities();
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, APPIUM_VERSION);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, WebDriverConfig.getInstance().getIOSPlatformVersion());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, WebDriverConfig.getInstance().getIOSDeviceName());
        capabilities.setCapability(IOSMobileCapabilityType.WAIT_FOR_APP_SCRIPT, "true");
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, "true");
        capabilities.setCapability(IOSMobileCapabilityType.NATIVE_WEB_TAP, "true");

        return capabilities;
    }

    public static DesiredCapabilities androidCapabilities() {
        DesiredCapabilities capabilities = mobileCapabilities();
        capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, APPIUM_VERSION);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, WebDriverConfig.getInstance().getAndroidPlatformVersion());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, WebDriverConfig.getInstance().getAndroidDeviceName());

        return capabilities;
    }

    private static DesiredCapabilities mobileCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, "true");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "0");

        if (!WebDriverConfig.getInstance().getMobileApplicationPath().isEmpty())
            capabilities.setCapability(MobileCapabilityType.APP, WebDriverConfig.getInstance().getMobileApplicationPath());

        return capabilities;
    }
}
