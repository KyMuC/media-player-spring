package ru.iteco.service.rest;

import ru.iteco.controller.dto.AlbumDto;

import java.util.Collection;
import java.util.UUID;

/**
 * Album REST service.
 *
 * @author Mikhail_Kudimov
 */
public interface RestAlbumService {

    /**
     * API for adding an album.
     */
    AlbumDto addAlbum(AlbumDto albumDto);

    /**
     * API for updating an album.
     */
    boolean updateAlbum(AlbumDto body);

    /**
     * API for finding albums by name.
     */
    Collection<AlbumDto> findByName(String name);

    /**
     * API for deleting an album.
     */
    boolean removeAlbum(UUID id);
}
