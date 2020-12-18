package ru.iteco.service.rest;

import ru.iteco.controller.dto.SongDto;

import java.util.Collection;
import java.util.UUID;

/**
 * Song REST service.
 *
 * @author Mikhail_Kudimov
 */
public interface RestSongService {

    /**
     * API for adding a song.
     */
    SongDto addSong(SongDto songDto);

    /**
     * API for updating a song.
     */
    boolean updateSongInfo(SongDto body);

    /**
     * API for finding songs by name.
     */
    Collection<SongDto> findByName(String name);

    /**
     * API for deleting a song.
     */
    boolean removeSong(UUID id);

}
