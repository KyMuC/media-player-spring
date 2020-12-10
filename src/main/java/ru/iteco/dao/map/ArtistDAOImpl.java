package ru.iteco.dao.map;

import org.springframework.stereotype.Repository;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ArtistDAOImpl extends AbstractDAO<UUID, Artist> implements ArtistDAO {

    public ArtistDAOImpl() {
        super(Artist.class, new HashMap<>());
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

    @Override
    public Collection<Artist> findByName(String name) {
        return items.values().stream().filter(artist -> artist.getName().equals(name)).collect(Collectors.toList());
    }
}
