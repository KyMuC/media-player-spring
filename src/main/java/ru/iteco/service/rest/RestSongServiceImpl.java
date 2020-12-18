package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.SongDto;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

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

    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;
    private final AlbumDAO albumDAO;

    public RestSongServiceImpl(SongDAO songDAO, ArtistDAO artistDAO, AlbumDAO albumDAO) {
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
        this.albumDAO = albumDAO;
    }

    @Override
    public SongDto addSong(SongDto songDto) {
        Song song = new Song(UUID.randomUUID(), songDto.getName());
        Artist artist = artistDAO.getByKey(songDto.getArtistId());
        artist.getSongs().add(song);

        songDAO.save(song);
        artistDAO.update(artist);

        if (songDto.getAlbumId() != null) {
            Album album = albumDAO.getById(songDto.getAlbumId());
            album.getSongs().add(song);
            albumDAO.update(album);
        }

        songDto.setId(song.getId());

        return songDto;
    }

    @Override
    public boolean updateSongInfo(SongDto body) {
        Song song = songDAO.getByKey(body.getId());

        if (song == null) return false;

        song.setName(body.getName());

        songDAO.update(song);

        return true;
    }

    @Override
    public Collection<SongDto> findByName(String name) {
        return songDAO.getByName(name).stream()
                .map(this::songToSongDto).collect(Collectors.toList());
    }

    @Override
    public boolean removeSong(UUID id) {
        Song song = songDAO.deleteByKey(id);

        return song != null;
    }

    private SongDto songToSongDto(Song song) {
        SongDto songDto = new SongDto();

        songDto.setId(song.getId());
        songDto.setName(song.getName());
        songDto.setArtistId(artistDAO.getBySong(song).getId());
        songDto.setAlbumId(
                ofNullable(albumDAO.getBySong(song))
                        .map(Album::getId)
                        .orElse(null)
        );

        return songDto;
    }
}
