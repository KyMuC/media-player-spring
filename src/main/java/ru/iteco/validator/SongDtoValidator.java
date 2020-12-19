package ru.iteco.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iteco.controller.dto.SongDto;
import ru.iteco.model.Song;
import ru.iteco.service.AlbumService;
import ru.iteco.service.ArtistService;

/**
 * SongDtoValidator.
 *
 * @author Mikhail_Kudimov
 */
@Component
public class SongDtoValidator implements Validator {

    private static final Logger logger = LogManager.getLogger(SongDtoValidator.class.getName());

    private final ArtistService artistService;
    private final AlbumService albumService;

    public SongDtoValidator(ArtistService artistService, AlbumService albumService) {
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SongDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SongDto songDto = (SongDto) target;

        if (artistService.getAll().stream().noneMatch(a -> a.getId().equals(songDto.getArtistId()))) {
            errors.rejectValue("artistId", "artist.notFound",
                    "No artist found with provided id.");
        }

        if (errors.hasErrors()) return;

        if (songDto.getAlbumId() != null && albumService.getAll().stream()
                .noneMatch(a -> a.getId().equals(songDto.getAlbumId()))) {
            errors.rejectValue("albumId", "album.notFound",
                    "No album found with id provided.");
        }
    }

    /**
     * SongDto validation on update API call.
     * @param target Object to validate
     * @param errors Contextual state about the validation process
     */
    public void validateUpdate(Object target, Errors errors) {
        SongDto songDto = (SongDto) target;

        if (songDto.getId() != null) {
            errors.rejectValue("id", "id.notAllowed",
                    "Id field not allowed for this operation.");
        }

        if (errors.hasErrors()) return;

        if (songDto.getArtistId() != null) {
            errors.rejectValue("artistId", "artistId.notAllowed",
                    "Artist Id field not allowed for this operation.");
        }

        if (errors.hasErrors()) return;

        if (songDto.getAlbumId() != null) {
            errors.rejectValue("albumId", "albumId.notAllowed",
                    "Album Id field not allowed for this operation.");
        }

        if (errors.hasErrors()) return;

        if (songDto.getName() == null) {
            errors.rejectValue("name", "name.notFound",
                    "New name field required.");
        }
    }
}
