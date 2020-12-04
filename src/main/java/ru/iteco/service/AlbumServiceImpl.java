package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@PropertySource(value = {"classpath:application.properties"})
public class AlbumServiceImpl implements AlbumService {

    @Value("${album.maxSongs}")
    private String maxSongs;
    private static final Logger logger = LogManager.getLogger(AlbumServiceImpl.class.getName());

    private final AlbumDAO albumDAO;

    public AlbumServiceImpl(AlbumDAO albumDAO) {
        logger.info("album service creation");
        this.albumDAO = albumDAO;
    }

    @Override
    public void addAlbum(Album album) {
        int size = ofNullable(album.getSongs())
                .map(List::size)
                .orElse(0);
        if(size > Integer.parseInt(maxSongs)) {
            throw new RuntimeException("An album cannot have more than " + maxSongs + " songs.");
        }
        albumDAO.save(album);
    }
}
