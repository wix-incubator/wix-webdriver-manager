package com.wixpress.automation.webdriver;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import com.wixpress.automation.webdriver.enums.RunOn;
import com.wixpress.automation.webdriver.enums.WebDriverStrategyType;
import com.wixpress.automation.webdriver.exceptions.WebDriverManagerException;
import com.wixpress.automation.webdriver.factory.CloudWebDriverFactory;
import com.wixpress.automation.webdriver.factory.LocalWebDriverFactory;
import com.wixpress.automation.webdriver.factory.WebDriverFactory;
import com.wixpress.automation.webdriver.strategy.NewInstanceWebDriverStrategy;
import com.wixpress.automation.webdriver.strategy.RecycledWebDriverStrategy;
import com.wixpress.automation.webdriver.strategy.WebDriverStrategy;
import org.openqa.selenium.WebDriver;

import static com.wixpress.automation.webdriver.enums.WebDriverStrategyType.RECYCLED;

public class WebDriverManager {

    private final WebDriverStrategy webDriverStrategy;

    public WebDriverManager() {
        webDriverStrategy = createStrategy();
    }

    public WebDriverManager(WebDriverConfig webDriverConfig) {
        WebDriverConfig.apply(webDriverConfig);
        webDriverStrategy = createStrategy();
    }

    public WebDriver getAvailableWebDriver() {
        return webDriverStrategy.get();
    }

    public void releaseWebDriver(WebDriver webDriver) {
        webDriverStrategy.release(webDriver);
    }

    public WebDriver recoverWebDriver(WebDriver webDriver) {
        return webDriverStrategy.recover(webDriver);
    }

    private void destroy() {
        webDriverStrategy.destroy();
    }

    private WebDriverStrategy createStrategy() {
        final WebDriverStrategyType strategyType = strategyType();

        if (strategyType == RECYCLED) {
            return new RecycledWebDriverStrategy(createFactory());
        } else if (strategyType == WebDriverStrategyType.NEW_INSTANCE) {
            return new NewInstanceWebDriverStrategy(createFactory());
        } else {
            throw new WebDriverManagerException("Failed to create web driver strategy with type: " + strategyType);
        }
    }

    private WebDriverStrategyType strategyType() {
        return WebDriverStrategyType.fromString(WebDriverConfig.getInstance().getStrategyType());
    }

    private WebDriverFactory createFactory() {
        switch (runOn()) {
            case LOCAL:
                return new LocalWebDriverFactory();
            case GRID:
                throw new WebDriverManagerException("Not implemented yet");
            case REMOTE:
                throw new WebDriverManagerException("Not implemented yet");
            case CLOUD:
                return new CloudWebDriverFactory();
            default:
                throw new WebDriverManagerException("Failed to create web driver factory with type: " + runOn());
        }
    }

    private RunOn runOn() {
        return RunOn.fromString(WebDriverConfig.getInstance().getRunOn());
    }
}
