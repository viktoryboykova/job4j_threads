package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchArrayIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public SearchArrayIndex(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
           return indexOf();
        }
        int mid = (from + to) / 2;
        SearchArrayIndex<T> leftSearch = new SearchArrayIndex<>(array, from, mid, value);
        SearchArrayIndex<T> rightSearch = new SearchArrayIndex<>(array, mid + 1, to, value);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left != -1 ? left : right;
    }

    private Integer indexOf() {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                result = i;
            }
        }
        return result;
    }

    public static <T> Integer search(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SearchArrayIndex<T> searchArrayIndex = new SearchArrayIndex<>(array, 0, array.length, value);
        return forkJoinPool.invoke(searchArrayIndex);
    }

    public static void main(String[] args) {
        String[] array1 = new String[]{"Egor", "Vika", "Kisa", "Arseniy", "Petr", "Helen", "Olga", "Max", "Andrey", "Tahir", "Anton"};
        Integer[] array2 = new Integer[]{1, 2, 3, 4, 5};
        System.out.println("Результат поиска в первом массиве, индекс элемента \"Kisa\": " + search(array1, "Kisa"));
        System.out.println("Результат поиска во втором массиве, индекс элемента \"1\": " + search(array2, 1));
    }
}
