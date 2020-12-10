package ru.iteco.dao;

import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

public interface ArtistDAO extends GenericDAO<UUID, Artist> {

    /**
     * Get artist given a certain song.
     * @param song Song used for lookup
     * @return Such artist if one exist, else - null
     */
    Artist getBySong(Song song);

    /**
     * Get artist given a certain album.
     * @param album Album used for lookup
     * @return Such artist if one exist, else - null
     */
    Artist getByAlbum(Album album);

    /**
     * Get artists with specified name.
     * @param name Artist's name
     * @return Collection of all artists with matching name
     */
    Collection<Artist> findByName(String name);
}
