package ru.iteco.service;

import ru.iteco.model.Album;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

/**
 * Album service dealing with album model operations.
 */
public interface AlbumService {

    /**
     * Add operation for new album.
     * @param album Album to add
     */
    void addAlbum(Album album);

    /**
     * Get album by its id.
     * @param id Id to lookup
     * @return Album with such id if one exists, else - null
     */
    Album findById(UUID id);

    /**
     * Get all existing albums.
     * @return Collection of all albums.
     */
    Collection<Album> getAll();

    /**
     * Update operation for album.
     * @param album Album to update
     */
    void updateAlbum(Album album);

    /**
     * Get album by one of its songs.
     * @param song Song to lookup
     * @return Album with such song if one exists, else - null
     */
    Album findBySong(Song song);

    /**
     * Delete operation for album by id.
     * @param id Id to lookup
     * @return Deleted album if successful, else - null
     */
    Album deleteByKey(UUID id);

    /**
     * Get albums with specified name.
     * @param name Name to lookup
     * @return Collection of albums with matching name
     */
    Collection<Album> findByName(String name);
}
