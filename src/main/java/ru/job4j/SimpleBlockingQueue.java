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
                System.out.println(Thread.currentThread().getName() + ": жду освобождение места...");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        System.out.println(Thread.currentThread().getName() + " добавил " + value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + ": жду пополнения очереди...");
            this.wait();
        }
        T value = queue.poll();
//        System.out.println(Thread.currentThread().getName() + " вернул " + value);
        notifyAll();
        return value;
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}