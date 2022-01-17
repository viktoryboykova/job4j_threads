package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        String output = "";
        int data;
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("src\\main\\java\\ru\\job4j\\io\\parse.txt");
        ParseFile parseFile = new ParseFile(file);
        Predicate<Character> filter1 = data -> true;
        System.out.println(parseFile.content(filter1));
    }
}
