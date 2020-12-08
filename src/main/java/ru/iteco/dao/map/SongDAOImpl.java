package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.Song;

import java.util.HashMap;
import java.util.UUID;

@Repository
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
