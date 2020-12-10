package ru.iteco.dao;

import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

public interface SongDAO extends GenericDAO<UUID, Song> {

    /**
     * Get all songs with specified name.
     * @param name Song's name
     * @return Collection of all songs with matching name
     */
    Collection<Song> getByName(String name);
}
