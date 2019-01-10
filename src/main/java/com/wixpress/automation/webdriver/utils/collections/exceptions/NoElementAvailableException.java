package com.wixpress.automation.webdriver.utils.collections.exceptions;

public class NoElementAvailableException extends RuntimeException {

    public NoElementAvailableException(int timeout) {
        super(String.format("No element available (timeout = %d ms)", timeout));
    }

    public NoElementAvailableException(int timeout, Throwable cause) {
        super(String.format("No element available (timeout = %d ms)", timeout), cause);
    }
}
