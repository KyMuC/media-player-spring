package ru.iteco.dao.map;

import org.springframework.context.annotation.Bean;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.dao.UserDAO;

/**
 * RepositoryConfig.
 *
 * @author Mikhail_Kudimov
 */
public class RepositoryConfig {

    @Bean
    public UserDAO userDAOImpl() {
        return new UserDAOImpl();
    }

    @Bean
    public ArtistDAO artistDAOImpl() {
        return new ArtistDAOImpl();
    }

    @Bean
    public AlbumDAO albumDAOImpl() {
        return new AlbumDAOImpl();
    }

    @Bean
    public SongDAO songDAOImpl() {
        return new SongDAOImpl();
    }
}
