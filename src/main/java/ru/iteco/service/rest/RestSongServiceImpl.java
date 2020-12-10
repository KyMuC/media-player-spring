package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.SongDto;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.service.AlbumService;
import ru.iteco.service.ArtistService;
import ru.iteco.service.SongService;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * RestSongImpl.
 *
 * @author Mikhail_Kudimov
 */
@Service
public class RestSongServiceImpl implements RestSongService {

    private static final Logger logger = LogManager.getLogger(RestSongServiceImpl.class.getName());

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    public RestSongServiceImpl(SongService songService, ArtistService artistService, AlbumService albumService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @Override
    public SongDto addSong(SongDto songDto) {
        Song song = new Song(UUID.randomUUID(), songDto.getName());
        Artist artist = artistService.findById(songDto.getArtistId());
        artist.getSongs().add(song);

        songService.addSong(song);
        artistService.updateArtist(artist);

        if (songDto.getAlbumId() != null) {
            Album album = albumService.findById(songDto.getAlbumId());
            album.getSongs().add(song);
            albumService.updateAlbum(album);
        }

        songDto.setId(song.getId());

        return songDto;
    }

    @Override
    public boolean updateSongInfo(UUID id, SongDto body) {
        Song song = songService.findById(id);

        if (song == null) return false;

        song.setName(body.getName());

        songService.updateSong(song);

        return true;
    }

    @Override
    public Collection<SongDto> findByName(String name) {
        return songService.findByName(name).stream()
                .map(this::songToSongDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeSong(UUID id) {
        Song song = songService.removeById(id);

        return song != null;
    }

    private SongDto songToSongDto(Song song) {
        SongDto songDto = new SongDto();

        songDto.setId(song.getId());
        songDto.setName(song.getName());
        songDto.setArtistId(artistService.findBySong(song).getId());
        songDto.setAlbumId(
                ofNullable(albumService.findBySong(song))
                        .map(Album::getId)
                        .orElse(null)
        );

        return songDto;
    }
}
