package com.karthikeyan.modules;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class SortedList<E> extends LinkedList<E> {
    private final Comparator<E> comparator;

    public SortedList(final Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Iterator<E> iterator() {
        this.sort(this.comparator);
        return super.iterator();
    }
}
