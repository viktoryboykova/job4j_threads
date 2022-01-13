package ru.job4j.concurrent;

import java.util.LinkedList;

public class ConsoleProgress implements Runnable {

    private LinkedList<String> symbols = new LinkedList<>();

    public ConsoleProgress() {
        symbols.add("\\");
        symbols.add("|");
        symbols.add("/");
        symbols.add("——");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String element = symbols.pollFirst();
                System.out.print("\r load: " + element);
                symbols.addLast(element);
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
