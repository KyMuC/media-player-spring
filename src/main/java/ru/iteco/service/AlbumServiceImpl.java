package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
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
public class AlbumServiceImpl implements AlbumService {

    private static final Logger logger = LogManager.getLogger(AlbumServiceImpl.class.getName());

    private final AlbumDAO albumDAO;

    public AlbumServiceImpl(AlbumDAO albumDAO, ArtistDAO artistDAO, UserDAO userDAO) {
        logger.info("album service creation");
        this.albumDAO = albumDAO;
    }

    @Override
    public void addAlbum(Album album) {
        albumDAO.save(album);
    }

    @Override
    public Album findById(UUID id) {
        return albumDAO.getById(id);
    }

    @Override
    public Collection<Album> getAll() {
        return albumDAO.getAll();
    }

    @Override
    public void updateAlbum(Album album) {
        albumDAO.update(album);
    }

    @Override
    public Album findBySong(Song song) {
        return albumDAO.getBySong(song);
    }

    @Override
    public Album deleteByKey(UUID id) {
        return albumDAO.deleteByKey(id);
    }

    @Override
    public Collection<Album> findByName(String name) {
        return albumDAO.getByName(name);
    }
}
