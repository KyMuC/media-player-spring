package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.Song;

@Service
public class SongServiceImpl implements SongService {

    private static final Logger logger = LogManager.getLogger(SongServiceImpl.class.getName());

    private final SongDAO songDAO;

    public SongServiceImpl(SongDAO songDAO) {
        logger.info("song service creation");
        this.songDAO = songDAO;
    }

    @Override
    public void addSong(Song song) {
        songDAO.save(song);
    }
}
