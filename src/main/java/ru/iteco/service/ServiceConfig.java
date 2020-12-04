package ru.iteco.service;

import org.springframework.context.annotation.Bean;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.dao.UserDAO;

/**
 * ServiceConfig.
 *
 * @author Mikhail_Kudimov
 */
public class ServiceConfig {

    @Bean
    public UserService userServiceImpl(UserDAO userDAO) {
        return new UserServiceImpl(userDAO);
    }

    @Bean
    public ArtistService artistServiceImpl(ArtistDAO artistDAO) {
        return new ArtistServiceImpl(artistDAO);
    }

    @Bean
    public AlbumService albumServiceImpl(AlbumDAO albumDAO) {
        return new AlbumServiceImpl(albumDAO);
    }

    @Bean
    public SongService songServiceImpl(SongDAO songDAO) {
        return new SongServiceImpl(songDAO);
    }
}
