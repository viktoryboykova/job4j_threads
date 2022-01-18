package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")

    private int size = 5;
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        while (queue.size() >= size) {
            try {
                System.out.println("Жду освобождение места...");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        System.out.println(value + " добавлен");
        notify();
    }

    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                System.out.println("Жду пополнения очереди...");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T value = queue.poll();
        System.out.println("Возвращаю число " + value);
        notify();
        return value;
    }

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i <= 100; i++) {
                        simpleBlockingQueue.offer(i);
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 1; i <= 100; i++) {
                        simpleBlockingQueue.poll();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
    }
}