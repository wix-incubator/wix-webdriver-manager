package com.wixpress.automation.webdriver.utils.functions.helpers;

public interface ThrowingSupplier<T> {
    /**
     * Gets a result.
     *
     * @return a result
     * @throws Throwable
     */
    T get() throws Throwable;
}
