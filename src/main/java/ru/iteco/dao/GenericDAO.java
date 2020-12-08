package ru.iteco.dao;

import ru.iteco.model.Identifiable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Unified interface controlling persistence objects' state.
 *
 * @param <K> Primary key type
 * @param <T> Persistence object type
 */
public interface GenericDAO<K extends Serializable, T extends Identifiable<K>> {

    /**
     * Create new record corresponding to item parameter.
     *
     * @param item Item to save
     * @return Added item
     */
    T save(T item);

    /**
     * Get item with relevant key.
     *
     * @param key Key of item to get
     * @return Item with relevant key or null, if no such item was found
     */
    T getByKey(K key);

    /**
     * Delete specified item.
     *
     * @param item Item to delete
     * @return Deleted item
     */
    T delete(T item);

    /**
     * Delete item by a specified key.
     *
     * @param key Key of item to delete
     * @return Deleted item
     */
    T deleteByKey(K key);

    /**
     * Update specified item.
     *
     * @param item Item with updated information
     * @return Updated item
     */
    T update(T item);

    /**
     * Get all records.
     *
     * @return Collection of all records
     */
    Collection<T> getAll();

    /**
     * Create records for all specified items.
     * @param items Items to add
     * @return Added items
     */
    Collection<T> addAll(Collection<T> items);
}
