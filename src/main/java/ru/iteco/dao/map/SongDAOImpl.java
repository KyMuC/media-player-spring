package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SongDAOImpl extends AbstractDAO<UUID, Song> implements SongDAO {

    public SongDAOImpl() {
        super(Song.class, new HashMap<>());
    }

    @Override
    public Collection<Song> getByName(String name) {
        return items.values().stream().filter(song -> song.getName().equals(name)).collect(Collectors.toList());
    }
}
