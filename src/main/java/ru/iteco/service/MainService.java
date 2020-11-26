package ru.iteco.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.UUID;

public class MainService {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        UserService userService = (UserService) context.getBean("userService");
        SongService songService = (SongService) context.getBean("songService");
        AlbumService albumService = (AlbumService) context.getBean("albumService");
        ArtistService artistService = (ArtistService) context.getBean("artistService");

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

        albumService.addAlbum(album);

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
