package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddAndCanNotRepeat() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Base");
        cache.add(base);
        assertThat(cache.size(), is(1));
        assertFalse(cache.add(base));
        assertThat(cache.size(), is(1));
    }

    @Test
    public void whenAddAndDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Base");
        cache.add(base);
        assertThat(cache.size(), is(1));
        cache.delete(base);
        assertThat(cache.size(), is(0));

    }

    @Test
    public void whenAddAndUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("Base1");
        cache.add(base1);
        assertThat(cache.get(1).getName(), is("Base1"));
        assertThat(cache.get(1).getVersion(), is(0));
        base1.setName("Base11");
        cache.update(base1);
        assertThat(cache.get(1).getName(), is("Base11"));
        assertThat(cache.get(1).getVersion(), is(1));
    }

}