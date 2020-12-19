package ru.iteco.dao.map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.iteco.dao.SongDAO;
import ru.iteco.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Lazy
@Repository
public class SongDAOImpl extends AbstractDAO<UUID, Song> implements SongDAO {

    private final ArtistDAOImpl artistDAO;
    private final AlbumDAOImpl albumDAO;
    private final UserDAOImpl userDAO;

    @Lazy
    public SongDAOImpl(ArtistDAOImpl artistDAO, AlbumDAOImpl albumDAO, UserDAOImpl userDAO) {
        super(Song.class, new HashMap<>());
        this.artistDAO = artistDAO;
        this.albumDAO = albumDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Collection<Song> getByName(String name) {
        return items.values().stream().filter(song -> song.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Song deleteByKey(UUID key) {
        Song song = getByKey(key);

        if (song != null) {
            Artist artist = artistDAO.getBySong(song);
            Album album = albumDAO.getBySong(song);

            if (album != null) {
                album.getSongs().remove(song);
                albumDAO.update(album);
            }

            artist.getSongs().remove(song);
            artistDAO.update(artist);

            List<User> users = userDAO.getAll().stream()
                    .filter(u -> u.getFavouriteSongs().contains(song))
                    .collect(Collectors.toList());

            users.forEach(u -> {
                u.getFavouriteSongs().remove(song);
                userDAO.update(u);
            });

            return super.deleteByKey(key);
        }

        return null;
    }
}
