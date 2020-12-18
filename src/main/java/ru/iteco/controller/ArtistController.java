package ru.iteco.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.iteco.controller.dto.ArtistDto;
import ru.iteco.service.rest.RestArtistService;
import ru.iteco.validator.ArtistDtoValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Artist REST APIs controller.
 *
 * @author Mikhail_Kudimov
 */
@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {

    private static final Logger logger = LogManager.getLogger(ArtistController.class.getName());

    private final RestArtistService restArtistService;
    private final ArtistDtoValidator artistDtoValidator;

    public ArtistController(RestArtistService restArtistService, ArtistDtoValidator artistDtoValidator) {
        this.restArtistService = restArtistService;
        this.artistDtoValidator = artistDtoValidator;
    }

    /**
     * Method for finding artists with relevant name.
     * @param name Artist name
     * @return Artists with specified name or artist with error if no artists were found
     */
    @GetMapping("{name}")
    public Collection<ArtistDto> getArtists(@PathVariable String name) {
        Collection<ArtistDto> artistDtos = restArtistService.findByName(name);

        if (artistDtos.size() == 0) {
            ArtistDto artistDto = new ArtistDto();
            artistDto.setErrors(Collections.singletonList(new ObjectError("artistDto",
                    new String[]{"artist.notFound"}, null,
                    "No artist found with such name.")));

            artistDtos.add(artistDto);
        }

        return artistDtos;
    }

    /**
     * Method for creating new artist.
     * @param body JSON body of artist to create
     * @return Newly created artist or artist with error if JSON format was wrong
     */
    @PostMapping
    public ArtistDto addArtist(@Validated @RequestBody ArtistDto body, BindingResult result,
                               HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        return restArtistService.addArtist(body);
    }

    /**
     * Method for updating an artist.
     * @param id Id of artist to update
     * @param body JSON-body with fields to update
     * @return Artist's updated fields or artist with error if no artist was found or JSON was in wrong format
     */
    @PutMapping("{id}")
    public ArtistDto updateArtist(@PathVariable UUID id, @RequestBody ArtistDto body, BindingResult result,
                                  HttpServletResponse httpServletResponse) {

        artistDtoValidator.validate(body, result);
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        body.setId(id);

        if (!restArtistService.updateArtistInfo(body)) {
            body.setErrors(Collections.singletonList(new ObjectError("artistDto",
                    new String[]{"artist.notFound"}, null,
                    "No artist found with such id.")));
        }

        return body;
    }

    /**
     * Method for delete artist by id.
     * @param id Id to delete artist by
     * @return Empty artist with HTTP status of 204 or artist with error if artist was not found
     */
    @DeleteMapping("{id}")
    public ArtistDto removeArtist(@PathVariable UUID id, HttpServletResponse httpServletResponse) {
        boolean deleted = restArtistService.removeArtist(id);

        ArtistDto artistDto = null;
        if (!deleted) {
            artistDto = new ArtistDto();
            artistDto.setErrors(Collections.singletonList(new ObjectError("artistDto",
                    new String[]{"artist.notFound"}, null,
                    "No artist found with such id.")));
        } else {
            httpServletResponse.setStatus(204);
        }

        return artistDto;
    }

    @ModelAttribute
    public ArtistDto artistDto() {
        return new ArtistDto();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(artistDtoValidator);
    }
}
