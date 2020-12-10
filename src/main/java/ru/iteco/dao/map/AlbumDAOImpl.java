package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AlbumDAOImpl extends AbstractDAO<UUID, Album> implements AlbumDAO {

    public AlbumDAOImpl() {
        super(Album.class, new HashMap<>());
    }

    @Override
    public Collection<Album> getByName(String name) {
        return items.values().stream().filter(album -> album.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Album getBySong(Song song) {
        return items.values().stream().filter(album -> album.getSongs().contains(song))
                .findFirst().orElse(null);
    }

    @Override
    public Album getById(UUID id) {
        return items.get(id);
    }
}
