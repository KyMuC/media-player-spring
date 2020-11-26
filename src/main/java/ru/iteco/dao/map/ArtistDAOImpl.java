package ru.iteco.dao.map;

import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArtistDAOImpl extends AbstractDAO<UUID, Artist> implements ArtistDAO {

    public ArtistDAOImpl() {
        super(Artist.class, new HashMap<>());
    }

    @Override
    public Artist getByName(String name) {
        return items.values().stream().filter(artist -> artist.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public Artist getBySong(Song song) {
        return items.values().stream().filter(artist -> artist.getSongs().contains(song))
                .findFirst().orElse(null);
    }

    @Override
    public Artist getByAlbum(Album album) {
        return items.values().stream().filter(artist -> artist.getAlbums().contains(album))
                .findFirst().orElse(null);
    }
}
