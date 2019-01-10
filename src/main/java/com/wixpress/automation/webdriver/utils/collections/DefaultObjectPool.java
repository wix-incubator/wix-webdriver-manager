package com.wixpress.automation.webdriver.utils.collections;

import com.google.common.collect.Iterators;
import com.wixpress.automation.webdriver.utils.collections.exceptions.NoElementAvailableException;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class DefaultObjectPool<E> implements ObjectPool<E> {

    private final Queue<E> available = new LinkedList<>();
    private final Set<E> occupied = new HashSet<>();

    @Override
    public int size() {
        return available.size() + occupied.size();
    }

    @Override
    public boolean isAvailable() {
        return available.size() > 0;
    }

    @Override
    public synchronized E get(int timeout) throws NoElementAvailableException {
        while (!isAvailable()) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
                throw new NoElementAvailableException(timeout, e);
            }
            if (!isAvailable()) {
                throw new NoElementAvailableException(timeout);
            }
        }

        E element = available.remove();
        occupied.add(element);
        return element;
    }

    @Override
    public synchronized void put(E element) {
        occupied.remove(element);
        available.add(element);
        notifyAll();
    }

    @Override
    public synchronized void putAndOccupy(E newElement) {
        occupied.add(newElement);
    }

    @Override
    public void fill(Supplier<? extends E> supplier, int amount) {
        IntStream.range(0, amount).forEach(__ -> put(supplier.get()));
    }

    @Override
    public synchronized void replace(E oldElement, E newElement) {
        if (occupied.contains(oldElement)) {
            occupied.remove(oldElement);
            occupied.add(newElement);
        } else {
            throw new RuntimeException("Replace failed, as old element is not in the pool");
        }
    }

    @Override
    public synchronized void clear() {
        available.clear();
        occupied.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return Iterators.concat(available.iterator(), occupied.iterator());
    }
}
