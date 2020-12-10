package ru.iteco.dao;

import ru.iteco.model.Album;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

/**
 * Interface controlling album persistence objects' state.
 */
public interface AlbumDAO extends GenericDAO<UUID, Album> {

    /**
     * Get albums with specified name.
     * @param name Album's name
     * @return Collection of all albums with matching name
     */
    Collection<Album> getByName(String name);

    /**
     * Get album that contains the specified song.
     * @param song Song used for lookup
     * @return Album that contains matching song. If song does not belong to any album - null.
     */
    Album getBySong(Song song);

    /**
     * Get song with matching id.
     * @param id Id used for lookup
     * @return Album matching given id
     */
    Album getById(UUID id);
}
