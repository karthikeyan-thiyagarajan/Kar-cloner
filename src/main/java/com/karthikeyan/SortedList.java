package com.karthikeyan;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class SortedList<E> extends LinkedList<E> {
    private Comparator<E> comparator;

    public SortedList(final Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Iterator<E> iterator() {
        Collections.sort(this, this.comparator);
        return super.iterator();
    }
}
