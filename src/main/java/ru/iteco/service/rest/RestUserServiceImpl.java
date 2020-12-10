package ru.iteco.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iteco.controller.dto.UserDto;
import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;
import ru.iteco.model.User;
import ru.iteco.service.AlbumService;
import ru.iteco.service.ArtistService;
import ru.iteco.service.SongService;
import ru.iteco.service.UserService;

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

    private final UserService userService;
    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    public RestUserServiceImpl(UserService userService, SongService songService, ArtistService artistService, AlbumService albumService) {
        this.userService = userService;
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User(UUID.randomUUID(), userDto.getUserName(), userDto.getPassword(), userDto.getEmail());
        userService.addUser(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    public boolean updateUserInfo(String query, UserDto body) {
        User user = userService.findByUserNameOrEmail(query);

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

        if (body.getFavouriteSongsIds() != null) {
            user.setFavouriteSongs(body.getFavouriteSongsIds().stream()
                    .map(songService::findById).collect(Collectors.toList())
            );
        }

        if (body.getFavouriteArtistsIds() != null) {
            user.setFavouriteArtists(body.getFavouriteArtistsIds().stream()
                    .map(artistService::findById).collect(Collectors.toList())
            );
        }

        if (body.getFavouriteAlbumsIds() != null) {
            user.setFavouriteAlbums(body.getFavouriteAlbumsIds().stream()
                    .map(albumService::findById).collect(Collectors.toList())
            );
        }

        userService.updateUser(user);

        return true;
    }

    @Override
    public UserDto findByUserNameOrEmail(String query) {
        User user = userService.findByUserNameOrEmail(query);

        if (user == null) {
            return null;
        }

        return userToUserDto(user);
    }

    @Override
    public boolean removeUser(String query) {
        User user = userService.deleteByUserNameOrEmail(query);

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
