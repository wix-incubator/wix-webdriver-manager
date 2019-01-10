package com.wixpress.automation.webdriver.utils.functions;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public final class Repeater {

    private Repeater() {
    }

    public static void repeat(int times, Runnable action) {
        IntStream.range(0, times).forEach(__ -> action.run());
    }

    public static void repeat(int times, Consumer<Integer> action) {
        IntStream.range(0, times).forEach(action::accept);
    }
}
