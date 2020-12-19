package ru.iteco.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iteco.controller.dto.AlbumDto;
import ru.iteco.service.ArtistService;
import ru.iteco.service.SongService;

/**
 * AlbumDtoValidator.
 *
 * @author Mikhail_Kudimov
 */
@Component
public class AlbumDtoValidator implements Validator {

    private static final Logger logger = LogManager.getLogger(AlbumDtoValidator.class.getName());

    private final ArtistService artistService;
    private final SongService songService;

    public AlbumDtoValidator(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AlbumDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AlbumDto albumDto = (AlbumDto) target;

        if (albumDto.getName() == null) {
            errors.rejectValue("name", "name.required", "Album name is required.");
        }

        if (errors.hasErrors()) return;

        if (albumDto.getArtistId() == null || artistService.getAll().stream()
                .noneMatch(a ->
                        albumDto.getArtistId().equals(a.getId())
                )) {
            errors.rejectValue("artistId", "artistId.notFound",
                    "No artist with provided artistId");
        }

        if (errors.hasErrors()) return;

        if (albumDto.getSongIds() != null && !albumDto.getSongIds().stream()
                .allMatch(songId -> songService.getAll().stream()
                        .anyMatch(s -> s.getId().equals(songId))
                )) {
            errors.rejectValue("songIds", "songId.notFound",
                    "Some of provided song ids do not correspond to existing songs.");
        }
    }

    /**
     * AlbumDto validation on update API call.
     * @param target Object to validate
     * @param errors Contextual state about the validation process
     */
    public void validateUpdate(Object target, Errors errors) {
        AlbumDto albumDto = (AlbumDto) target;

        if (albumDto.getArtistId() != null) {
            errors.rejectValue("artistId", "artistId.notAllowed",
                    "Changing artist not allowed for albums.");
        }

        if (errors.hasErrors()) return;

        if (albumDto.getSongIds() != null && !albumDto.getSongIds().stream()
                .allMatch(songId -> songService.getAll().stream()
                        .anyMatch(s -> s.getId().equals(songId))
                )) {
            errors.rejectValue("songIds", "songId.notFound",
                    "Some of provided song ids do not correspond to existing songs.");
        }
    }
}
