package ru.iteco.service;

import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;

/**
 * Song service dealing with song model operations.
 */
public interface SongService {

    /**
     * Add operation for new song.
     * @param song Song to add
     */
    void addSong(Song song);

    /**
     * Update operation for existing song.
     * @param song Song to update
     */
    void updateSong(Song song);

    /**
     * Get song by its id.
     * @param id Id to lookup
     * @return Song with such id if one exists, else - null
     */
    Song findById(UUID id);

    /**
     * Get all exists songs.
     * @return Collection of all songs
     */
    Collection<Song> getAll();

    /**
     * Get songs with specified name.
     * @param name Name to lookup
     * @return Collection of songs with matching name.
     */
    Collection<Song> findByName(String name);

    /**
     * Remove a song by its id.
     * @param id Id of song to delete.
     * @return Deleted songs if successful, else - null
     */
    Song removeById(UUID id);
}
