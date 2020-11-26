package ru.iteco.dao.map;

import ru.iteco.dao.SongDAO;
import ru.iteco.model.Song;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SongDAOImpl extends AbstractDAO<UUID, Song> implements SongDAO {

    public SongDAOImpl() {
        super(Song.class, new HashMap<>());
    }

    @Override
    public Song getByName(String name) {
        return items.values().stream().filter(song -> song.getName().equals(name))
                .findFirst().orElse(null);
    }
}
