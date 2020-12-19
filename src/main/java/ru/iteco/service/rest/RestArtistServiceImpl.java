package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.ArtistDto;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * RestArtistServiceImpl.
 *
 * @author Mikhail_Kudimov
 */
@Service
public class RestArtistServiceImpl implements RestArtistService {

    private static final Logger logger = LogManager.getLogger(RestArtistServiceImpl.class.getName());

    private final ArtistDAO artistDAO;
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;

    public RestArtistServiceImpl(ArtistDAO artistDAO, SongDAO songDAO, AlbumDAO albumDAO) {
        this.artistDAO = artistDAO;
        this.songDAO = songDAO;
        this.albumDAO = albumDAO;
    }

    @Override
    public ArtistDto addArtist(ArtistDto artistDto) {
        Artist artist = new Artist(UUID.randomUUID(), artistDto.getName(), artistDto.getCountryOfOrigin());
        artistDAO.save(artist);
        artistDto.setId(artist.getId());

        return artistDto;
    }

    @Override
    public boolean updateArtistInfo(ArtistDto body) {
        Artist artist = artistDAO.getByKey(body.getId());

        if (artist == null) return false;

        artist.setName(body.getName());

        artist.setCountryOfOrigin(body.getCountryOfOrigin());

        artist.setSongs(body.getSongIds().stream()
                .map(songDAO::getByKey)
                .collect(Collectors.toList())
        );

        artist.setAlbums(body.getAlbumIds().stream()
                .map(albumDAO::getById)
                .collect(Collectors.toList())
        );

        artistDAO.update(artist);

        return true;
    }

    @Override
    public Collection<ArtistDto> findByName(String name) {
        return artistDAO.findByName(name).stream()
                .map(this::artistToArtistDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeArtist(UUID id) {
        Artist artist = artistDAO.deleteByKey(id);

        return artist != null;
    }

    private ArtistDto artistToArtistDto(Artist artist) {
        ArtistDto artistDto = new ArtistDto();

        artistDto.setId(artist.getId());
        artistDto.setName(artist.getName());
        artistDto.setCountryOfOrigin(artist.getCountryOfOrigin());

        if (artist.getSongs() != null) {
            artistDto.setSongIds(artist.getSongs().stream().map(Song::getId).collect(Collectors.toList()));
        }

        if (artist.getAlbums() != null) {
            artistDto.setAlbumIds(artist.getAlbums().stream().map(Album::getId).collect(Collectors.toList()));
        }

        return artistDto;
    }
}
