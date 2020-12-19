package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.UserDto;
import ru.iteco.dao.AlbumDAO;
import ru.iteco.dao.ArtistDAO;
import ru.iteco.dao.SongDAO;
import ru.iteco.dao.UserDAO;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.model.User;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * RegistrationServiceImpl.
 *
 * @author Mikhail_Kudimov
 */
@Service
public class RestUserServiceImpl implements RestUserService {

    private static final Logger logger = LogManager.getLogger(RestUserServiceImpl.class.getName());

    private final UserDAO userDAO;
    private final SongDAO songDAO;
    private final ArtistDAO artistDAO;
    private final AlbumDAO albumDAO;

    public RestUserServiceImpl(UserDAO userDAO, SongDAO songDAO, ArtistDAO artistDAO, AlbumDAO albumDAO) {
        this.userDAO = userDAO;
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
        this.albumDAO = albumDAO;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User(UUID.randomUUID(), userDto.getUserName(), userDto.getPassword(), userDto.getEmail());
        userDAO.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    public boolean updateUserInfo(String query, UserDto body) {
        User user = userDAO.findByUserNameOrEmail(query);

        if (user == null) return false;

        if (body.getUserName() != null) {
            user.setUserName(body.getUserName());
        }

        if (body.getEmail() != null) {
            user.setEmail(body.getEmail());
        }

        if (body.getPassword() != null) {
            user.setPasswordHash(body.getPassword());
        }

        user.setFavouriteSongs(body.getFavouriteSongsIds().stream()
                .map(songDAO::getByKey).collect(Collectors.toList())
        );

        user.setFavouriteArtists(body.getFavouriteArtistsIds().stream()
                .map(artistDAO::getByKey).collect(Collectors.toList())
        );


        user.setFavouriteAlbums(body.getFavouriteAlbumsIds().stream()
                .map(albumDAO::getByKey).collect(Collectors.toList())
        );

        userDAO.update(user);

        return true;
    }

    @Override
    public UserDto findByUserNameOrEmail(String query) {
        User user = userDAO.findByUserNameOrEmail(query);

        if (user == null) {
            return null;
        }

        return userToUserDto(user);
    }

    @Override
    public boolean removeUser(String query) {
        User user = userDAO.deleteByUserNameOrEmail(query);

        return user != null;
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setPasswordHash(user.getPasswordHash());
        userDto.setFavouriteSongsIds(user.getFavouriteSongs().stream()
                .map(Song::getId)
                .collect(Collectors.toList())
        );
        userDto.setFavouriteArtistsIds(user.getFavouriteArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toList())
        );
        userDto.setFavouriteAlbumsIds(user.getFavouriteAlbums().stream()
                .map(Album::getId)
                .collect(Collectors.toList())
        );

        return userDto;
    }
}
