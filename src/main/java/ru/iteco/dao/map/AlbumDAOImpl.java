package ru.iteco.dao.map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Lazy
@Repository
public class AlbumDAOImpl extends AbstractDAO<UUID, Album> implements AlbumDAO {

    private final ArtistDAOImpl artistDAO;
    private final UserDAOImpl userDAO;

    @Lazy
    public AlbumDAOImpl(ArtistDAOImpl artistDAO, UserDAOImpl userDAO) {
        super(Album.class, new HashMap<>());
        this.artistDAO = artistDAO;
        this.userDAO = userDAO;
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

    @Override
    public Album deleteByKey(UUID key) {
        Album album = getById(key);

        if (album != null) {
            Artist artist = artistDAO.getByAlbum(album);

            artist.getAlbums().remove(album);
            artistDAO.update(artist);

            List<User> users = userDAO.getAll().stream()
                    .filter(u -> u.getFavouriteAlbums().contains(album))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.getFavouriteAlbums().remove(album);
                userDAO.update(u);
            });

            return super.deleteByKey(key);
        }

        return null;
    }
}
