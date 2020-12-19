package ru.iteco.service.rest;

import ru.iteco.controller.dto.ArtistDto;

import java.util.Collection;
import java.util.UUID;

/**
 * Artist REST service.
 *
 * @author Mikhail_Kudimov
 */
public interface RestArtistService {

    /**
     * API for adding an artist.
     */
    ArtistDto addArtist(ArtistDto artistDto);

    /**
     * API for updating an artist.
     */
    boolean updateArtistInfo(ArtistDto body);

    /**
     * API for finding artists by name.
     */
    Collection<ArtistDto> findByName(String name);

    /**
     * API for deleting an artist.
     */
    boolean removeArtist(UUID id);
}
