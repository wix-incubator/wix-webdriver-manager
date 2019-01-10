package com.wixpress.automation.webdriver.utils.functions.helpers;

import static com.wixpress.automation.webdriver.utils.functions.ContainerUtils.safeInvoke;

public interface ThrowingRunnable {

    /**
     * Performs required action
     *
     * @throws Throwable
     */
    void run() throws Throwable;

    /**
     * Returns a "silent" runnable, which performs given action and silences exceptions
     *
     * @return "silent" runnable
     */
    default Runnable silentRunnable() {
        return () -> safeInvoke(this);
    }
}
