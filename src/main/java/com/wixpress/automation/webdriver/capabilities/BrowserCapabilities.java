package com.wixpress.automation.webdriver.capabilities;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BrowserCapabilities {

    private BrowserCapabilities() {
    }

    public static ChromeOptions getChromeOptions() {
        final String language = WebDriverConfig.getInstance().getLanguageCode();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type", "start-maximized", "disable-popup-blocking", "disable-infobars");
        options.addArguments(String.format("--lang=%s", language));

        if (WebDriverConfig.getInstance().isHeadlessMode()) {
            System.out.println("RUNNING NEW HEADLESS MODE");
            options.setHeadless(true);
        }

        if (WebDriverConfig.getInstance().isMobileUserAgent()) {
            options.addArguments("--user-agent=Mobile Safari/602.1");

            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "IPhone 6");

            Map<String, Object> chromeOptions = new HashMap<>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
            options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }

        if (WebDriverConfig.getInstance().isEnableWebDriverLogging()) {
            LoggingPreferences logPref = new LoggingPreferences();
            logPref.enable(LogType.BROWSER, Level.ALL);
            logPref.enable(LogType.PERFORMANCE, Level.ALL);
            logPref.enable(LogType.DRIVER, Level.ALL);
            options.setCapability(CapabilityType.LOGGING_PREFS, logPref);
        }

        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);

        if (WebDriverConfig.getInstance().isHeadlessMode()) {
            System.out.println("RUNNING NEW HEADLESS MODE");
            options.setHeadless(true);
        }

        return options;
    }

    public static InternetExplorerOptions getExplorerOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return options;
    }

    public static EdgeOptions getEdgeOptions() {
        return new EdgeOptions();
    }

    public static SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setCapability("cleanSession", true);
        return options;
    }
}
