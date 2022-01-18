package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> in = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        Thread third = new Thread(() -> list.add(3));
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2, 3)));
    }

    @Test
    public void testChangedCollections() {
        List<Integer> in = new ArrayList<>();
        in.add(1);
        SingleLockList<Integer> singleList = new SingleLockList<>(in);
        singleList.add(3);
        assertThat(singleList.size(), is(2));
        assertThat(in.size(), is(1));
    }

    @Test
    public void testIterator() {
        List<Integer> in = List.of(1, 2);
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), is(2));
        assertFalse(it.hasNext());
        list.add(3);
        assertFalse(it.hasNext());
    }

    @Test(expected = AssertionError.class)
    public void testIteratorFailedAssertionError() {
        List<Integer> in = List.of(1, 2);
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), is(2));
        assertFalse(it.hasNext());
        list.add(3);
        assertTrue(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorFailedNoSuchElementException() {
        List<Integer> in = List.of(1, 2);
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), is(2));
        list.add(3);
        assertThat(it.next(), is(3));
    }
}