package ru.iteco.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.iteco.controller.dto.AlbumDto;
import ru.iteco.service.rest.RestAlbumService;
import ru.iteco.validator.AlbumDtoValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Album REST APIs controller.
 *
 * @author Mikhail_Kudimov
 */
@RestController
@RequestMapping("/api/v1/album")
public class AlbumController {

    private static final Logger logger = LogManager.getLogger(AlbumController.class.getName());

    private final RestAlbumService restAlbumService;
    private final AlbumDtoValidator albumDtoValidator;

    public AlbumController(RestAlbumService restAlbumService, AlbumDtoValidator albumDtoValidator) {
        this.restAlbumService = restAlbumService;
        this.albumDtoValidator = albumDtoValidator;
    }

    /**
     * Method for finding albums with relevant name.
     * @param name Name to find albums by
     * @return Albums with specified name or empty album with an error if no albums were found
     */
    @GetMapping("{name}")
    public Collection<AlbumDto> getAlbums(@PathVariable String name) {
        Collection<AlbumDto> albumDtos = restAlbumService.findByName(name);

        if (albumDtos.size() == 0) {
            AlbumDto albumDto = new AlbumDto();
            albumDto.setErrors(Collections.singletonList(new ObjectError("albumDto",
                    new String[]{"album.notFound"}, null,
                    "No album found with such name.")));

            albumDtos.add(albumDto);
        }

        return albumDtos;
    }

    /**
     * Method for adding new album.
     * @param body JSON body of album to create
     * @return The newly created album or album with error if JSON format is wrong
     */
    @PostMapping
    public AlbumDto addAlbum(@Validated @RequestBody AlbumDto body, BindingResult result,
                             HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        return restAlbumService.addAlbum(body);
    }

    /**
     * Method for updating album info.
     * @param id Id to find album by
     * @param body JSON body with fields to update
     * @return JSON with updated fields or JSON with fields to update and error if JSON was wrong
     */
    @PutMapping("{id}")
    public AlbumDto updateAlbum(@PathVariable UUID id, @RequestBody AlbumDto body, BindingResult result,
                                HttpServletResponse httpServletResponse) {
        albumDtoValidator.validateUpdate(body, result);
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        if (!restAlbumService.updateAlbum(id, body)) {
            body.setErrors(Collections.singletonList(new ObjectError("albumDto",
                    new String[]{"album.notFound"}, null,
                    "No album found with such id.")));
        }

        return body;
    }

    /**
     * Method for deleting a specified album by id.
     * @param id Id to delete album by
     * @return Empty album with HTTP status of 204 or album with error if album was not found
     */
    @DeleteMapping("{id}")
    public AlbumDto removeAlbum(@PathVariable UUID id, HttpServletResponse httpServletResponse) {
        boolean deleted = restAlbumService.removeAlbum(id);

        AlbumDto albumDto = null;
        if (!deleted) {
            albumDto = new AlbumDto();
            albumDto.setErrors(Collections.singletonList(new ObjectError("albumDto",
                    new String[]{"album.notFound"}, null,
                    "No album found with such id.")));
        } else {
            httpServletResponse.setStatus(204);
        }

        return albumDto;
    }

    @ModelAttribute
    public AlbumDto albumDto() {
        return new AlbumDto();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(albumDtoValidator);
    }
}
