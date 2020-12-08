package ru.iteco.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.iteco.config.SpringConfig;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.UUID;

@Component
public class MainService {

    private static final Logger logger = LogManager.getLogger(MainService.class.getName());

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        UserService userService = (UserService) context.getBean("userServiceImpl");
        SongService songService = (SongService) context.getBean("songServiceImpl");
        AlbumService albumService = (AlbumService) context.getBean("albumServiceImpl");
        ArtistService artistService = (ArtistService) context.getBean("artistServiceImpl");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("john_doe");
        user.setEmail("j.doe@i-teco.ru");
        user.setPasswordHash("mypass");

        userService.addUser(user);

        Song song = new Song();
        song.setId(UUID.randomUUID());
        song.setName("Riptide");

        songService.addSong(song);

        Album album = new Album();
        album.setId(UUID.randomUUID());
        album.setName("God Loves You When You're Dancing");
        album.setSongs(Collections.singletonList(song));

        try {
            albumService.addAlbum(album);
        } catch (RuntimeException e) {
            logger.error("RuntimeException: " + e);
        }

        Artist artist = new Artist();
        artist.setId(UUID.randomUUID());
        artist.setName("Vance Joy");
        artist.setCountryOfOrigin("Australia");
        artist.setSongs(Collections.singletonList(song));
        artist.setAlbums(Collections.singletonList(album));

        artistService.addArtist(artist);

        user.setFavouriteSongs(Collections.singletonList(song));
        user.setFavouriteAlbums(Collections.singletonList(album));
        user.setFavouriteArtists(Collections.singletonList(artist));

        userService.updateUser(user);
    }
}
