package ru.iteco.model;

import java.io.Serializable;

/**
 * Identifiable objects' interface.
 *
 * @param <T> ID type
 */
public interface Identifiable<T extends Serializable> extends Serializable {

    /**
     * Get object ID.
     * @return Object ID
     */
    T getId();
}
