package ru.iteco.service;

import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

/**
 * Artist service dealing with artist model operations.
 */
public interface ArtistService {

    /**
     * Add operation for new artist.
     * @param artist Artist to add
     */
    void addArtist(Artist artist);

    /**
     * Get artist by id.
     * @param id Id to lookup
     * @return Artist with such id if one exists, else - null
     */
    Artist findById(UUID id);

    /**
     * Get all exists artists.
     * @return Collection of all artists.
     */
    Collection<Artist> getAll();

    /**
     * Update operation for existing artist.
     * @param artist Artist to update.
     */
    void updateArtist(Artist artist);

    /**
     * Get artists with specifies name.
     * @param name Name used for lookup
     * @return Collection of artists with matching name
     */
    Collection<Artist> findByName(String name);

    /**
     * Get artist by their song.
     * @param song Song to lookup
     * @return Artist with such song if one exists, else - null
     */
    Artist findBySong(Song song);

    /**
     * Get artist by their album.
     * @param album Album to lookup
     * @return Artist with such song if one exists, else - null
     */
    Artist findByAlbum(Album album);

    /**
     * Delete artist by id.
     * @param id Id to lookup
     * @return Deleted artist is successful, else - null
     */
    Artist deleteByKey(UUID id);
}
