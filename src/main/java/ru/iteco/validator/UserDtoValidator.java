package ru.iteco.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iteco.controller.dto.UserDto;
import ru.iteco.service.AlbumService;
import ru.iteco.service.ArtistService;
import ru.iteco.service.SongService;
import ru.iteco.service.UserService;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * UserDtoValidator.
 *
 * @author Mikhail_Kudimov
 */
@Component
public class UserDtoValidator implements Validator {

    private static final Logger logger = LogManager.getLogger(UserDtoValidator.class.getName());

    private final UserService userService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;

    public UserDtoValidator(UserService userService, AlbumService albumService, ArtistService artistService, SongService songService) {
        this.userService = userService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        if (userDto.getUserName().isEmpty()) {
            errors.rejectValue("userName", "userName.empty",
                    "Username should not be empty.");
        }

        if (errors.hasErrors()) return;

        if (userDto.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.empty", "Email should not be empty");
        }

        if (errors.hasErrors()) return;

        if (!Objects.equals(userDto.getPassword(), userDto.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "password.mismatch",
                    "Passwords do not match.");
        }

        if (errors.hasErrors()) return;

        if (emailRegExInvalid(userDto.getEmail())) {
            errors.rejectValue("email", "email.wrongFormat",
                    "Provided email is not valid.");
        }

        if (errors.hasErrors()) return;

        if (userService.emailTaken(userDto.getEmail())) {
            errors.rejectValue("email", "email.taken", "Provided email already exists.");
        }

        if (errors.hasErrors()) return;

        if (userService.userNameTaken(userDto.getUserName())) {
            errors.rejectValue("userName", "userName.taken",
                    "Provided user name already exists.");
        }
    }

    /**
     * UserDto validation on update API call.
     * @param target Object to validate
     * @param errors Contextual state about the validation process
     */
    public void validateUpdate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        if (userDto.getEmail() != null) {
            if (emailRegExInvalid(userDto.getEmail())) {
                errors.rejectValue("email", "email.wrongFormat",
                        "Provided email is not valid.");
            }

            if (errors.hasErrors()) return;

            if (userService.emailTaken(userDto.getEmail())) {
                errors.rejectValue("email", "email.taken",
                        "Provided email already exists");
            }
        }

        if (errors.hasErrors()) return;

        if (userDto.getPassword() != null && userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "password.mismatch",
                    "Passwords do not match.");
        }

        if (errors.hasErrors()) return;

        if (userDto.getUserName() != null && userService.userNameTaken(userDto.getUserName())) {
            errors.rejectValue("userName", "userName.taken",
                    "Provided user name already exists.");
        }

        if (errors.hasErrors()) return;

        if (userDto.getFavouriteAlbumsIds() != null && !userDto.getFavouriteAlbumsIds().stream()
                .allMatch(albumId -> albumService.getAll().stream()
                        .anyMatch(a -> a.getId().equals(albumId))
                )) {
            errors.rejectValue("favouriteAlbumsIds", "albumId.notFound",
                    "Some of provided favourite album ids do not correspond to existing albums.");
        }

        if (errors.hasErrors()) return;

        if (userDto.getFavouriteArtistsIds() != null && !userDto.getFavouriteArtistsIds().stream()
                .allMatch(artistId -> artistService.getAll().stream()
                        .anyMatch(a -> a.getId().equals(artistId))
                )) {
            errors.rejectValue("favouriteArtistsIds", "artistId.notFound",
                    "Some of provided favourite artist ids do not correspond to existing artists.");
        }

        if (errors.hasErrors()) return;

        if (userDto.getFavouriteSongsIds() != null && !userDto.getFavouriteSongsIds().stream()
                .allMatch(songId -> songService.getAll().stream()
                        .anyMatch(s -> s.getId().equals(songId))
                )) {
            errors.rejectValue("favouriteSongsIds", "songId.notFound",
                    "Some of provided favourite song ids do not correspond to existing songs.");
        }
    }

    private boolean emailRegExInvalid(String email) {
        return !Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
                .matcher(email).find();
    }
}
