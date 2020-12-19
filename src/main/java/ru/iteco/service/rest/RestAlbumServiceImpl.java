package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.AlbumDto;
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
 * RestAlbumServiceImpl.
 *
 * @author Mikhail_Kudimov
 */
@Service
public class RestAlbumServiceImpl implements RestAlbumService {

    private static final Logger logger = LogManager.getLogger(RestAlbumServiceImpl.class.getName());

    private final AlbumDAO albumDAO;
    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;

    public RestAlbumServiceImpl(AlbumDAO albumDAO, SongDAO songDAO, ArtistDAO artistDAO) {
        this.albumDAO = albumDAO;
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
    }

    @Override
    public AlbumDto addAlbum(AlbumDto albumDto) {
        Album album = new Album(UUID.randomUUID(), albumDto.getName(), albumDto.getSongIds().stream()
                .map(songDAO::getByKey)
                .collect(Collectors.toList())
        );

        albumDAO.save(album);

        Artist artist = artistDAO.getByKey(albumDto.getArtistId());
        artist.getAlbums().add(album);

        artistDAO.update(artist);

        albumDto.setId(album.getId());
        return albumDto;
    }

    @Override
    public boolean updateAlbum(AlbumDto body) {
        Album album = albumDAO.getById(body.getId());

        if (album == null) return false;

        album.setName(body.getName());

        album.setSongs(body.getSongIds().stream()
                .map(songDAO::getByKey)
                .collect(Collectors.toList())
        );

        albumDAO.update(album);

        return true;
    }

    @Override
    public Collection<AlbumDto> findByName(String name) {
        return albumDAO.getByName(name).stream().map(this::albumToAlbumDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeAlbum(UUID id) {
        Album album = albumDAO.deleteByKey(id);

        return album != null;
    }

    private AlbumDto albumToAlbumDto(Album album) {
        AlbumDto albumDto = new AlbumDto();

        albumDto.setId(album.getId());
        albumDto.setName(album.getName());

        albumDto.setArtistId(artistDAO.getByAlbum(album).getId());

        albumDto.setSongIds(album.getSongs().stream().map(Song::getId).collect(Collectors.toList()));

        return albumDto;
    }
}
