package com.wixpress.automation.webdriver.utils.functions;

import com.wixpress.automation.webdriver.utils.functions.helpers.Consumers;
import com.wixpress.automation.webdriver.utils.functions.helpers.ThrowingConsumer;
import com.wixpress.automation.webdriver.utils.functions.helpers.ThrowingRunnable;
import com.wixpress.automation.webdriver.utils.functions.helpers.ThrowingSupplier;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ContainerUtils {
    private ContainerUtils() {
    }

    /**
     * Safely retrieves the first value from given suppliers (by order): returns an empty optional if all threw exceptions
     *
     * @param suppliers value supplier methods to invoke
     * @param <T>       value type
     * @return empty or supplied value
     */
    @SafeVarargs
    public static <T> Optional<T> safeGet(ThrowingSupplier<T>... suppliers) {
        return Stream.of(suppliers)
                .map(ContainerUtils::safeGet)
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

    /**
     * Safely retrieves the first value from given suppliers (by order): returns an empty optional if all threw exceptions
     *
     * @param suppliers value supplier methods to invoke
     * @param <T>       value type
     * @return empty or supplied value
     */
    @SafeVarargs
    public static <T> Optional<T> safeGet(Predicate<? super T> predicate, ThrowingSupplier<T>... suppliers) {
        return Stream.of(suppliers)
                .map(s -> safeGet(predicate, s))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

    /**
     * Safely performs given action on each item in given iterable, using given error consumer to keep errors
     *
     * @param iterable      items to iterate over
     * @param action        action to perform
     * @param errorConsumer error consumer to use
     * @param <T>           item type
     */
    public static <T> void safeForEach(Iterable<T> iterable, ThrowingConsumer<T> action, Consumer<Throwable> errorConsumer) {
        iterable.forEach(t -> safeInvoke(() -> action.accept(t), errorConsumer));
    }

    /**
     * Safely performs given action on each item in given iterable while silencing all errors
     *
     * @param iterable items to iterate over
     * @param action   action to perform
     * @param <T>      item type
     */
    public static <T> void safeForEach(Iterable<T> iterable, ThrowingConsumer<T> action) {
        safeForEach(iterable, action, Consumers.empty());
    }

    /**
     * Safely invokes given runnable, using given error consumer to keep errors
     *
     * @param runnable      runnable to invoke
     * @param errorConsumer error consumer to use
     * @return true if successful, false otherwise
     */
    public static boolean safeInvoke(ThrowingRunnable runnable, Consumer<Throwable> errorConsumer) {
        try {
            runnable.run();
            return true;
        } catch (Throwable t) {
            errorConsumer.accept(t);
            return false;
        }
    }

    /**
     * Safely invokes given runnable
     *
     * @param runnable runnable to invoke
     * @return true if successful, false otherwise
     */
    public static boolean safeInvoke(ThrowingRunnable runnable) {
        return safeInvoke(runnable, Consumers.empty());
    }

    /**
     * Flattens input collection to a single list
     *
     * @param input input collection
     * @return flattened list
     */
    public static List<Object> flatten(Collection<Object[]> input) {
        return input.stream()
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    /**
     * Safely retrieves value from given supplier: returns an empty optional in case some exception was thrown,
     * or the supplied value in case of normal execution
     *
     * @param method value supplier method to invoke
     * @param <T>    value type
     * @return empty or supplied value
     */
    private static <T> Optional<T> safeGet(ThrowingSupplier<T> method) {
        try {
            return Optional.ofNullable(method.get());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    /**
     * Safely retrieves value from given supplier: returns an empty optional in case some exception was thrown,
     * or the supplied value in case of normal execution
     *
     * @param method value supplier method to invoke
     * @param <T>    value type
     * @return empty or supplied value
     */
    private static <T> Optional<T> safeGet(Predicate<? super T> predicate, ThrowingSupplier<T> method) {
        return safeGet(method).filter(predicate);
    }

    /**
     * Helper class for invoking custom actions only once
     */
    public static class InvokeOnce {

        private final AtomicBoolean shouldInvoke = new AtomicBoolean(true);
        private final Runnable action;

        public InvokeOnce(Runnable action) {
            this.action = action;
        }

        public void invoke() {
            if (shouldInvoke.get()) {
                synchronized (this) {
                    if (shouldInvoke.get() && shouldInvoke.getAndSet(false)) {
                        action.run();
                    }
                }
            }
        }
    }
}
