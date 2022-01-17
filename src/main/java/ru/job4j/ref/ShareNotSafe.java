package ru.job4j.ref;

import java.util.ArrayList;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user2 = User.of("name2");
        User user3 = User.of("name3");
        User user4 = User.of("name4");
        User user5 = User.of("name5");
        cache.add(user);
        cache.add(user2);
        cache.add(user3);
        cache.add(user4);
        cache.add(user5);
        Thread first = new Thread(
                () -> {
                    user2.setName("rename2");
                    ArrayList<User> list = (ArrayList<User>) cache.findAll();
                    list.get(0).setName("rename");
                }
        );
        first.start();
        first.join();
        for (User us : cache.findAll()) {
            System.out.println(us.getName());
        }
    }
}
