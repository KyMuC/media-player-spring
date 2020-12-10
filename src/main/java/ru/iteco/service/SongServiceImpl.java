package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.dao.UserDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.model.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private static final Logger logger = LogManager.getLogger(SongServiceImpl.class.getName());

    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;
    private final AlbumDAO albumDAO;
    private final UserDAO userDAO;

    public SongServiceImpl(SongDAO songDAO, ArtistDAO artistDAO, AlbumDAO albumDAO, UserDAO userDAO) {
        logger.info("song service creation");
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
        this.albumDAO = albumDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void addSong(Song song) {
        songDAO.save(song);
    }

    @Override
    public void updateSong(Song song) {
        songDAO.update(song);
    }

    @Override
    public Song findById(UUID id) {
        return songDAO.getByKey(id);
    }

    @Override
    public Collection<Song> getAll() {
        return songDAO.getAll();
    }

    @Override
    public Collection<Song> findByName(String name) {
        return songDAO.getByName(name);
    }

    @Override
    public Song removeById(UUID id) {
        Song song = songDAO.getByKey(id);

        if (song != null) {
            Artist artist = artistDAO.getBySong(song);
            Album album = albumDAO.getBySong(song);

            if (album != null) {
                album.getSongs().remove(song);
                albumDAO.update(album);
            }

            artist.getSongs().remove(song);
            artistDAO.update(artist);

            List<User> users = userDAO.getAll().stream()
                    .filter(u -> u.getFavouriteSongs().contains(song))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.getFavouriteSongs().remove(song);
                userDAO.update(u);
            });

            return songDAO.deleteByKey(id);
        }

        return null;
    }
}
