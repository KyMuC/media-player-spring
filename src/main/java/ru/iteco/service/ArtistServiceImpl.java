package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.Artist;

public class ArtistServiceImpl implements ArtistService {

    private static final Logger logger = LogManager.getLogger(ArtistServiceImpl.class.getName());

    private final ArtistDAO artistDAO;

    public ArtistServiceImpl(ArtistDAO artistDAO) {
        logger.info("artist service creation");
        this.artistDAO = artistDAO;
    }

    @Override
    public void addArtist(Artist artist) {
        artistDAO.save(artist);
    }
}
