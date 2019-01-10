package com.wixpress.automation.webdriver.utils.collections;

import com.wixpress.automation.webdriver.utils.collections.exceptions.NoElementAvailableException;

import java.util.function.Supplier;

public interface ObjectPool<E> extends Iterable<E> {

    int DEFAULT_TIMEOUT = 5 * 60 * 1000; // 5 minutes

    /**
     * @return pool size
     */
    int size();

    /**
     * @return true if this pool is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return true if there is any available element in this pool, false otherwise
     */
    boolean isAvailable();

    /**
     * Returns first available element in this pool, blocking the calling thread
     * until some element is available or given timeout has passed
     *
     * @param timeout maximum amount of time to wait in milliseconds
     * @return element
     * @throws NoElementAvailableException
     */
    E get(int timeout) throws NoElementAvailableException;

    /**
     * Returns first available element in this pool, blocking the calling thread
     * until some element is available or timeout of DEFAULT_TIMEOUT has passed
     *
     * @return element
     * @throws NoElementAvailableException
     */
    default E get() throws NoElementAvailableException {
        return get(DEFAULT_TIMEOUT);
    }

    /**
     * Inserts given element to this pool
     *
     * @param element element to insert
     */
    void put(E element);

    /**
     * Inserts given element to this pool and occupies it
     *
     * @param element element to insert
     */
    void putAndOccupy(E element);

    /**
     * Fills this object pool with elements according to given amount
     *
     * @param supplier element supplier
     * @param amount   amount of elements to create
     */
    void fill(Supplier<? extends E> supplier, int amount);

    /**
     * Replaces given element in this pool
     *
     * @param oldElement element to remove
     * @param newElement element to insert
     */
    void replace(E oldElement, E newElement);

    /**
     * Clears all elements in this pool
     */
    void clear();
}
