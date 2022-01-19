package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Job> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 1; i <= size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                tasks.poll().run();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }, "Поток №" + i
            ));
        }
    }

    public void work(Job job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (Thread thread : threadPool.threads) {
            System.out.println(thread.getName() + " " + thread.getState());
        }
        for (Thread thread : threadPool.threads) {
            thread.start();
        }
        for (Thread thread : threadPool.threads) {
            System.out.println(thread.getName() + " " + thread.getState());
        }
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            Runnable runnable = () -> System.out.println("Задача №" + finalI + " выполнена с помощью " + Thread.currentThread().getName());
            threadPool.work(new Job(runnable, String.valueOf(finalI)));
        }
        threadPool.shutdown();
        for (Thread thread : threadPool.threads) {
            thread.join();
        }
        for (Thread thread : threadPool.threads) {
            System.out.println(thread.getName() + " " + thread.getState());
        }
    }
}
