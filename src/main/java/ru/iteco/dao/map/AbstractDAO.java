package ru.iteco.dao.map;

import ru.iteco.dao.GenericDAO;
import ru.iteco.model.Identifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractDAO<K extends Serializable, T extends Identifiable<K>> implements GenericDAO<K, T> {

    private Class<T> clazz;

    protected Map<K, T> items;

    public AbstractDAO(Class<T> itemClass, Map<K, T> items) {
        clazz = itemClass;
        this.items = items;
    }

    @Override
    public T save(T item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public T getByKey(K key) {
        return items.get(key);
    }

    @Override
    public T delete(T item) {
        return deleteByKey(item.getId());
    }

    @Override
    public T deleteByKey(K key) {
        return items.remove(key);
    }

    @Override
    public T update(T item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Collection<T> getAll() {
        return items.values();
    }

    @Override
    public Collection<T> addAll(Collection<T> items) {
        items.forEach(i -> this.items.put(i.getId(), i));
        return items;
    }
}
