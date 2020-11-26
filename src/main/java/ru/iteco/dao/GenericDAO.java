package ru.iteco.dao;

import ru.iteco.model.Identifiable;

import java.io.Serializable;
import java.util.Collection;

public interface GenericDAO<K extends Serializable, T extends Identifiable<K>> {

    T save(T item);

    T getByKey(K key);

    T delete(T item);

    T deleteByKey(K key);

    T update(T item);

    Collection<T> getAll();

    Collection<T> addAll(Collection<T> items);
}
