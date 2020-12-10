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
public class ArtistServiceImpl implements ArtistService {

    private static final Logger logger = LogManager.getLogger(ArtistServiceImpl.class.getName());

    private final ArtistDAO artistDAO;
    private final SongDAO songDAO;
    private final AlbumDAO albumDAO;
    private final UserDAO userDAO;

    public ArtistServiceImpl(ArtistDAO artistDAO, SongDAO songDAO, AlbumDAO albumDAO, UserDAO userDAO) {
        logger.info("artist service creation");
        this.albumDAO = albumDAO;
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void addArtist(Artist artist) {
        artistDAO.save(artist);
    }

    @Override
    public Artist findById(UUID id) {
        return artistDAO.getByKey(id);
    }

    @Override
    public Collection<Artist> getAll() {
        return artistDAO.getAll();
    }

    @Override
    public void updateArtist(Artist artist) {
        artistDAO.update(artist);
    }

    @Override
    public Collection<Artist> findByName(String name) {
        return artistDAO.findByName(name);
    }

    @Override
    public Artist findBySong(Song song) {
        return artistDAO.getBySong(song);
    }

    @Override
    public Artist findByAlbum(Album album) {
        return artistDAO.getByAlbum(album);
    }

    @Override
    public Artist deleteByKey(UUID id) {
        Artist artist = artistDAO.getByKey(id);

        if (artist != null) {
            artist.getSongs().forEach(s ->
                    songDAO.deleteByKey(s.getId())
            );

            artist.getAlbums().forEach(a ->
                    albumDAO.deleteByKey(a.getId())
            );

            List<User> users = userDAO.getAll().stream()
                    .filter(u -> u.getFavouriteArtists().contains(artist))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.getFavouriteArtists().remove(artist);
                userDAO.update(u);
            });

            return artistDAO.deleteByKey(id);
        }

        return null;
    }
}
