package ru.job4j.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(() -> {
           String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
           String body = String.format("Add a new event to %s", user.getEmail());
            try {
                send(subject, body, user.getEmail());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) throws InterruptedException {
        System.out.println("_________________________________________________" + "\n" + "Message to " + email
        + "\n" + subject + "\n" + body + "\n" + "_________________________________________________" + "\n" + "\n");
        Thread.sleep(5000);
    }

    public static void main(String[] args) throws InterruptedException {
        EmailNotification emailNotification = new EmailNotification();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(
                    () -> {
                emailNotification.emailTo(new User("Name " + finalI, finalI + "_email@mail.ru"));
            }, "Thread" + i
            );
            thread.start();
            thread.join();
        }
        emailNotification.close();
    }
}
