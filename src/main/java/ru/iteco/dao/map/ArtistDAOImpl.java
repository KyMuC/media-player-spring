package ru.iteco.dao.map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Lazy
@Repository
public class ArtistDAOImpl extends AbstractDAO<UUID, Artist> implements ArtistDAO {

    private final SongDAOImpl songDAO;
    private final AlbumDAOImpl albumDAO;
    private final UserDAOImpl userDAO;

    @Lazy
    public ArtistDAOImpl(SongDAOImpl songDAO, AlbumDAOImpl albumDAO, UserDAOImpl userDAO) {
        super(Artist.class, new HashMap<>());
        this.songDAO = songDAO;
        this.albumDAO = albumDAO;
        this.userDAO = userDAO;
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

    @Override
    public Artist deleteByKey(UUID key) {
        Artist artist = getByKey(key);

        if (artist != null) {
            artist.getSongs().forEach(s ->
                    songDAO.deleteByKey(s.getId())
            );

            artist.getAlbums().forEach(a ->
                    albumDAO.deleteByKey(a.getId())
            );

            List<User> users = userDAO.getAll().stream()
                    .filter(u -> u.getFavouriteArtists().contains(artist))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.getFavouriteArtists().remove(artist);
                userDAO.update(u);
            });

            return super.deleteByKey(key);
        }

        return null;
    }
}
