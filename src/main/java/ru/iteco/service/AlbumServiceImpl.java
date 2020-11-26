package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;

public class AlbumServiceImpl implements AlbumService {

    private static final Logger logger = LogManager.getLogger(AlbumServiceImpl.class.getName());

    private final AlbumDAO albumDAO;

    public AlbumServiceImpl(AlbumDAO albumDAO) {
        logger.info("album service creation");
        this.albumDAO = albumDAO;
    }

    @Override
    public void addAlbum(Album album) {
        albumDAO.save(album);
    }
}
