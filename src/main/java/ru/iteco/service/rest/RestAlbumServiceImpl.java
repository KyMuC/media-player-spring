package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.AlbumDto;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.service.AlbumService;
import ru.iteco.service.ArtistService;
import ru.iteco.service.SongService;

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

    private final AlbumService albumService;
    private final SongService songService;
    private final ArtistService artistService;

    public RestAlbumServiceImpl(AlbumService albumService, SongService songService, ArtistService artistService) {
        this.albumService = albumService;
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    public AlbumDto addAlbum(AlbumDto albumDto) {
        Album album = new Album(UUID.randomUUID(), albumDto.getName(), albumDto.getSongIds().stream()
                .map(songService::findById)
                .collect(Collectors.toList())
        );

        albumService.addAlbum(album);

        Artist artist = artistService.findById(albumDto.getArtistId());
        artist.getAlbums().add(album);

        artistService.updateArtist(artist);

        albumDto.setId(album.getId());
        return albumDto;
    }

    @Override
    public boolean updateAlbum(UUID id, AlbumDto body) {
        Album album = albumService.findById(id);

        if (album == null) return false;

        if (body.getName() != null) {
            album.setName(body.getName());
        }

        if (body.getSongIds() != null) {
            album.setSongs(body.getSongIds().stream()
                    .map(songService::findById)
                    .collect(Collectors.toList())
            );
        }

        albumService.updateAlbum(album);

        return true;
    }

    @Override
    public Collection<AlbumDto> findByName(String name) {
        return albumService.findByName(name).stream().map(this::albumToAlbumDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeAlbum(UUID id) {
        Album album = albumService.deleteByKey(id);

        return album != null;
    }

    private AlbumDto albumToAlbumDto(Album album) {
        AlbumDto albumDto = new AlbumDto();

        albumDto.setId(album.getId());
        albumDto.setName(album.getName());

        albumDto.setArtistId(artistService.findByAlbum(album).getId());

        albumDto.setSongIds(album.getSongs().stream().map(Song::getId).collect(Collectors.toList()));

        return albumDto;
    }
}
