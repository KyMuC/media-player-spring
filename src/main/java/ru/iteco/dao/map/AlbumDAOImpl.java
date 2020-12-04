package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Song;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class AlbumDAOImpl extends AbstractDAO<UUID, Album> implements AlbumDAO {

    public AlbumDAOImpl() {
        super(Album.class, new HashMap<>());
    }

    @Override
    public Album getByName(String name) {
        return items.values().stream().filter(album -> album.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public Album getBySong(Song song) {
        return items.values().stream().filter(album -> album.getSongs().contains(song))
                .findFirst().orElse(null);
    }
}
