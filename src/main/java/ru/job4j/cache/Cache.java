package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (i, b) -> {
                    if (b.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    Base updateBase = new Base(model.getId(), model.getVersion() + 1);
                    updateBase.setName(model.getName());
                    return updateBase;
                }
        ) != null;

    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public int size() {
        return memory.size();
    }
}
