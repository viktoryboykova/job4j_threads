package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveContentFile {
    private final File file;

    public SaveContentFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("src\\main\\java\\ru\\job4j\\io\\save.txt");
        SaveContentFile saveContentFile = new SaveContentFile(file1);
        File file2 = new File("src\\main\\java\\ru\\job4j\\io\\parse.txt");
        ParseFile parseFile = new ParseFile(file2);
        String content = parseFile.content(data -> true);
        saveContentFile.saveContent(content);
    }
}
