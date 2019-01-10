package com.wixpress.automation.webdriver.factory;

import com.wixpress.automation.webdriver.capabilities.PlatformCapabilities;
import com.wixpress.automation.webdriver.config.WebDriverConfig;
import com.wixpress.automation.webdriver.enums.CloudService;
import com.wixpress.automation.webdriver.enums.WebDriverType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class CloudWebDriverFactory  implements WebDriverFactory {

    private final String USERNAME = WebDriverConfig.getInstance().getCloudUserName();
    private final String PASSWORD = WebDriverConfig.getInstance().getCloudPassword();

    private static final String SELENIUM_VERSION = WebDriverConfig.getInstance().getSeleniumVersion();
    private static final String CHROME_VERSION = WebDriverConfig.getInstance().getChromeVersion();
    private static final String CHROME_DRIVER_VERSION = WebDriverConfig.getInstance().getChromeDriverVersion();
    private static final String FIREFOX_VERSION = WebDriverConfig.getInstance().getFirefoxVersion();
    private static final String SAFARI_VERSION = WebDriverConfig.getInstance().getSafariVersion();
    private static final String IE_VERSION = WebDriverConfig.getInstance().getIEVersion();
    private static final String IE_DRIVER_VERSION = WebDriverConfig.getInstance().getIEDriverVersion();
    private static final String EDGE_VERSION = WebDriverConfig.getInstance().getEdgeVersion();

    private String cloudUrl;

    @Override
    public WebDriver create(WebDriverType webDriverType) {
        DesiredCapabilities capabilities =  PlatformCapabilities.defaultCapabilities();

        cloudUrl = getCloudUrl();

        switch (webDriverType) {
            case CHROME:
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                capabilities.setCapability(CapabilityType.VERSION, CHROME_VERSION);
                capabilities.setCapability("chromedriverVersion", CHROME_DRIVER_VERSION);
                break;
            case FIREFOX:
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
                capabilities.setCapability(CapabilityType.VERSION, FIREFOX_VERSION);
                break;
            case EDGE:
                capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE);
                capabilities.setCapability("platform", Platform.WINDOWS);
                capabilities.setCapability(CapabilityType.VERSION, EDGE_VERSION);
                capabilities.setCapability("platform", "Windows 10");
                capabilities.setCapability("version", EDGE_VERSION);
                break;
            case EXPLORER:
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
                capabilities.setCapability(CapabilityType.VERSION, IE_VERSION);
                capabilities.setCapability("iedriverVersion", IE_DRIVER_VERSION);
                break;
            case SAFARI:
                capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
                capabilities.setCapability(CapabilityType.VERSION, SAFARI_VERSION);
                capabilities.setCapability("platform", Platform.SIERRA);
                capabilities.setCapability("seleniumVersion", SELENIUM_VERSION);
                break;
            case IOS:
                return startCloudForMobile(PlatformCapabilities.iOSCapabilities());
            case ANDROID:
                return startCloudForMobile(PlatformCapabilities.androidCapabilities());
            default:
                throw new RuntimeException("Unsupported browser / platform: " + webDriverType);
        }

        return startCloudForDesktop(capabilities);
    }

    private WebDriver startCloudForMobile(DesiredCapabilities capabilities) {
        try {
            if (capabilities.getCapability("platformName").equals("iOS")) {
                return new IOSDriver(new URL(cloudUrl), capabilities);
            } else {
                return new AndroidDriver(new URL(cloudUrl), capabilities);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to create mobile web driver. Url: " + cloudUrl);
        }
    }

    private WebDriver startCloudForDesktop(DesiredCapabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(cloudUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to create web driver. Url: " + cloudUrl);
        }
    }

    private String getCloudUrl() {
        switch(CloudService.fromString(WebDriverConfig.getInstance().getCloudService())) {
            case SAUCE_LABS:
                return "http://" + USERNAME + ":" + PASSWORD + "@ondemand.saucelabs.com:80/wd/hub";
            case BROWSER_STACK:
                return "https://" + USERNAME + ":" + PASSWORD + "@hub-cloud.browserstack.com/wd/hub";
            default:
                throw new RuntimeException("Unsupported cloud service: " + WebDriverConfig.getInstance().getCloudService());
        }
    }
}
