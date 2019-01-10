package com.wixpress.automation.webdriver.strategy;

import com.wixpress.automation.webdriver.config.WebDriverConfig;
import com.wixpress.automation.webdriver.exceptions.WebDriverManagerException;
import com.wixpress.automation.webdriver.factory.WebDriverFactory;
import com.wixpress.automation.webdriver.utils.collections.DefaultObjectPool;
import com.wixpress.automation.webdriver.utils.collections.ObjectPool;
import com.wixpress.automation.webdriver.utils.collections.exceptions.NoElementAvailableException;
import com.wixpress.automation.webdriver.utils.functions.helpers.ThrowingSupplier;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static com.wixpress.automation.webdriver.utils.functions.ContainerUtils.safeGet;
import static com.wixpress.automation.webdriver.utils.functions.ContainerUtils.safeInvoke;

public class RecycledWebDriverStrategy  implements WebDriverStrategy {

    private static final int MAX_SIZE = WebDriverConfig.getInstance().numberOfThreads();

    private static final int WEB_DRIVER_INIT_TIMEOUT = 30000;
    private static final int DRIVER_USAGE_LIMIT = WebDriverConfig.getInstance().webDriverRecycleLimit();

    private final ObjectPool<WebDriver> webDrivers = new DefaultObjectPool<>();
    private final Map<Integer, Integer> webDriverUsages = new ConcurrentHashMap<>();

    private final WebDriverFactory webDriverFactory;

    public RecycledWebDriverStrategy(WebDriverFactory webDriverFactory) {
        this.webDriverFactory = webDriverFactory;
        webDrivers.fill(this::createNewWebDriver, MAX_SIZE);
    }

    @Override
    public WebDriver get() {
        try {
            WebDriver recycledWebDriver = webDrivers.get();
            int recycledWebDriverHashCode = recycledWebDriver.hashCode();

            if (webDriverUsages.get(recycledWebDriverHashCode) < DRIVER_USAGE_LIMIT) {
                webDriverUsages.merge(recycledWebDriverHashCode, 1, (v1, v2) -> v1 + v2);
                return recycledWebDriver;
            } else {
                return recover(recycledWebDriver);
            }
        } catch (NoElementAvailableException e) {
            throw new RuntimeException("Failed to get web driver", e);
        }
    }

    @Override
    public void release(WebDriver webDriver) {
        webDrivers.put(webDriver);
    }

    @Override
    public WebDriver recover(WebDriver webDriver) {
        safeInvoke(webDriver::quit);
        WebDriver newWebDriver = webDriverFactory.create();
        webDrivers.replace(webDriver, newWebDriver);
        webDriverUsages.remove(webDriver.hashCode());
        webDriverUsages.merge(newWebDriver.hashCode(), 1, (v1, v2) -> v1 + v2);
        return newWebDriver;
    }

    @Override
    public void destroy() {
        webDrivers.forEach(WebDriver::quit);
        webDrivers.clear();
        webDriverUsages.clear();
    }

    private WebDriver createNewWebDriver() {
        WebDriver webDriver = tryCreateWithRetry();
        webDrivers.putAndOccupy(webDriver);
        webDriverUsages.merge(webDriver.hashCode(), 0, (v1, v2) -> v1 + v2);
        return webDriver;
    }

    private WebDriver tryCreateWithRetry() {
        return safeGet(this::tryCreateWithTimeout, this::tryCreateWithTimeout)
                .orElseThrow(() -> new WebDriverManagerException("Failed to create web driver"));
    }

    private WebDriver tryCreateWithTimeout() {
        return executeWithTimeout((ThrowingSupplier<WebDriver>) webDriverFactory::create, WEB_DRIVER_INIT_TIMEOUT).get();
    }

    private static <T> Optional<T> executeWithTimeout(ThrowingSupplier<T> action, int timeoutMs) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            return safeGet(new ThrowingSupplier[]{action});
        });

        try {
            executor.shutdown();
            return (Optional)future.get((long)timeoutMs, TimeUnit.MILLISECONDS);
        } catch (Throwable var5) {
            future.cancel(true);
            throw new RuntimeException("Failed to execute action with timeout: " + timeoutMs, var5);
        }
    }
}
