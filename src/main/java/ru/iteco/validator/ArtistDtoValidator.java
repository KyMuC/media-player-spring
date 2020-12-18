package ru.iteco.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iteco.controller.dto.ArtistDto;
import ru.iteco.service.AlbumService;
import ru.iteco.service.SongService;

/**
 * ArtistDtValidator.
 *
 * @author Mikhail_Kudimov
 */
@Component
public class ArtistDtoValidator implements Validator {

    private static final Logger logger = LogManager.getLogger(ArtistDtoValidator.class.getName());

    private final SongService songService;
    private final AlbumService albumService;

    public ArtistDtoValidator(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ArtistDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArtistDto artistDto = (ArtistDto) target;

        if (artistDto.getName() == null) {
            errors.rejectValue("name", "name.notPresent", "Name field required");
        }

        if (errors.hasErrors()) return;

        if (artistDto.getAlbumIds() != null && artistDto.getAlbumIds().size() > 0) {
            errors.rejectValue("albumIds", "albumIds.notAllowed",
                    "Album references should be created using update API.");
        }

        if (errors.hasErrors()) return;

        if (artistDto.getSongIds() != null && artistDto.getSongIds().size() > 0) {
            errors.rejectValue("songIds", "songsIds.notAllowed",
                    "Song references should be created using update API.");
        }
    }

    /**
     * ArtistDto validation on update API call.
     * @param target Object to validate
     * @param errors Contextual state about the validation process
     */
    public void validateUpdate(Object target, Errors errors) {
        ArtistDto artistDto = (ArtistDto) target;

        if (artistDto.getSongIds() != null && !artistDto.getSongIds().stream()
                .allMatch(songId -> songService.getAll().stream()
                        .anyMatch(s -> s.getId().equals(songId))
                )) {
            errors.rejectValue("songsIds", "songsId.notFound",
                    "Some of provided song ids do not correspond to existing songs.");
        }

        if (errors.hasErrors()) return;

        if (artistDto.getAlbumIds() != null && !artistDto.getAlbumIds().stream()
                .allMatch(albumId -> albumService.getAll().stream()
                        .anyMatch(a -> a.getId().equals(albumId))
                )) {
            errors.rejectValue("albumIds", "albumId.notFound",
                    "Some of provided album ids do not correspond to existing albums.");
        }
    }
}
