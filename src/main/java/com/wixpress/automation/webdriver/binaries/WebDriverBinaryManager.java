package com.wixpress.automation.webdriver.binaries;

import com.wixpress.automation.webdriver.enums.WebDriverType;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class WebDriverBinaryManager {

    private WebDriverBinaryManager() {
    }

    public static void install(WebDriverType webDriverType) {
        installBrowserBinary(webDriverType);
    }

    private static void installBrowserBinary(WebDriverType webDriverType) {
        WebDriverBinaryVersion binariesVersion = new WebDriverBinaryVersion();

        try {
            switch (webDriverType) {
                case CHROME:
                    ChromeDriverManager.chromedriver()
                            .version(binariesVersion.chromeDriverBinaryVersion())
                            .setup();
                    break;
                case FIREFOX:
                    FirefoxDriverManager.firefoxdriver()
                            .version(binariesVersion.firefoxDriverBinaryVersion())
                            .setup();
                    break;
                case EXPLORER:
                    InternetExplorerDriverManager.iedriver()
                            .version(binariesVersion.internetExplorerBinaryVersion())
                            .setup();
                    break;
            }
        } catch (RuntimeException e) {
            String errorMessage = String.format("Could not install or recognize previous %s webDriver installation. " +
                    "Try connect to Internet and run again", webDriverType.toString().toUpperCase());
            System.out.print(errorMessage);
            System.out.print(e);
        }
    }
}
