package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.ArtistDto;
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
 * RestArtistServiceImpl.
 *
 * @author Mikhail_Kudimov
 */
@Service
public class RestArtistServiceImpl implements RestArtistService {

    private static final Logger logger = LogManager.getLogger(RestArtistServiceImpl.class.getName());

    private final ArtistService artistService;
    private final SongService songService;
    private final AlbumService albumService;

    public RestArtistServiceImpl(ArtistService artistService, SongService songService, AlbumService albumService) {
        this.artistService = artistService;
        this.songService = songService;
        this.albumService = albumService;
    }

    @Override
    public ArtistDto addArtist(ArtistDto artistDto) {
        Artist artist = new Artist(UUID.randomUUID(), artistDto.getName(), artistDto.getCountryOfOrigin());
        artistService.addArtist(artist);
        artistDto.setId(artist.getId());

        return artistDto;
    }

    @Override
    public boolean updateArtistInfo(UUID id, ArtistDto body) {
        Artist artist = artistService.findById(id);

        if (artist == null) return false;

        if (body.getName() != null) {
            artist.setName(body.getName());
        }

        if (body.getCountryOfOrigin() != null) {
            artist.setCountryOfOrigin(body.getCountryOfOrigin());
        }

        if (body.getSongIds() != null) {
            artist.setSongs(body.getSongIds().stream()
                    .map(songService::findById)
                    .collect(Collectors.toList())
            );
        }

        if (body.getAlbumIds() != null) {
            artist.setAlbums(body.getAlbumIds().stream()
                    .map(albumService::findById)
                    .collect(Collectors.toList())
            );
        }

        artistService.updateArtist(artist);

        return true;
    }

    @Override
    public Collection<ArtistDto> findByName(String name) {
        return artistService.findByName(name).stream()
                .map(this::artistToArtistDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeArtist(UUID id) {
        Artist artist = artistService.deleteByKey(id);

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
