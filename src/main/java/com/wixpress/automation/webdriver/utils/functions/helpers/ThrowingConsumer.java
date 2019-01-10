package com.wixpress.automation.webdriver.utils.functions.helpers;

public interface ThrowingConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Throwable;
}
