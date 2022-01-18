package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenAddAndGetAll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> test = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i <= 1000; i++) {
                        queue.offer(i);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 1; i <= 1000; i++) {
                        test.add(queue.poll());
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            expected.add(i);
        }
        assertThat(test, is(expected));
    }

}