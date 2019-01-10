package com.wixpress.automation.webdriver.exceptions;

public class WebDriverManagerException extends RuntimeException {

    public WebDriverManagerException(String message) {
        super(message);
    }

    public WebDriverManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
