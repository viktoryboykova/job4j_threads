package ru.job4j.concurrent;

public class ThreadStopTwo {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(10000);
                            System.out.println("end ...");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
        System.out.println("before");
        progress.join();
        System.out.println("after");
    }
}
