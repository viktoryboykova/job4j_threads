package ru.job4j;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CountJCIPTest {
    private class ThreadCount extends Thread {
        private final CountJCIP count;

        private ThreadCount(final CountJCIP count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        /* Создаем счетчик. */
        final CountJCIP count = new CountJCIP();
        /* Создаем нити. */
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get(), is(2));

    }
}