package com.wixpress.automation.webdriver.utils.functions.helpers;

import java.util.function.Consumer;

public final class Consumers {

    private static final Consumer<?> EMPTY = __ -> {
    };

    private Consumers() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> empty() {
        return (Consumer<T>) EMPTY;
    }
}
