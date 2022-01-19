package ru.job4j.pool;

public class Job implements Runnable {
    private final Runnable runnable;
    private final String name;

    public Job(Runnable runnable, String name) {
        this.runnable = runnable;
        this.name = name;
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public String toString() {
        return "Job{"
                + "name='" + name + '\''
                + '}';
    }
}
