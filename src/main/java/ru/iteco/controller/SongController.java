package ru.iteco.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.iteco.controller.dto.SongDto;
import ru.iteco.service.rest.RestSongService;
import ru.iteco.validator.SongDtoValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Song REST APIs controller.
 *
 * @author Mikhail_Kudimov
 */
@RestController
@RequestMapping("/api/song")
public class SongController {

    private static final Logger logger = LogManager.getLogger(SongController.class.getName());

    private final SongDtoValidator songDtoValidator;
    private final RestSongService restSongService;

    public SongController(SongDtoValidator songDtoValidator, RestSongService restSongService) {
        this.songDtoValidator = songDtoValidator;
        this.restSongService = restSongService;
    }

    /**
     * Method for finding songs with relevant name.
     * @param query Name of songs to find
     * @return Songs with relevant name or song with error if no song was found
     */
    @GetMapping("/info")
    public Collection<SongDto> getSongs(@RequestParam(name = "q") String query) {
        Collection<SongDto> songDtos = restSongService.findByName(query);

        if (songDtos.size() == 0) {
            SongDto songDto = new SongDto();
            songDto.setErrors(Collections.singletonList(new ObjectError("songDto",
                    new String[]{"song.notFound"}, null,
                    "No song found with such name.")));

            songDtos.add(songDto);
        }

        return songDtos;
    }

    /**
     * Method for creating a new song.
     * @param body JSON body of song to create
     * @return Newly created song or song with error if JSON format was wrong
     */
    @PostMapping("/add")
    public SongDto addSong(@Validated @RequestBody SongDto body, BindingResult result,
                           HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        return restSongService.addSong(body);
    }

    /**
     * Method for updating song by id.
     * @param id Id of song to update
     * @param body JSON body with fields to update
     * @return JSON with updated fields or song with error if no song was found or JSON was wrong
     */
    @PutMapping("/update")
    public SongDto updateSong(@RequestParam("id") UUID id, @RequestBody SongDto body, BindingResult result,
                              HttpServletResponse httpServletResponse) {
        songDtoValidator.validateUpdate(body, result);
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        if (!restSongService.updateSongInfo(id, body)) {
            body.setErrors(Collections.singletonList(new ObjectError("songDto",
                    new String[]{"song.notFound"}, null,
                    "No song found with such id.")));
        }

        return body;
    }

    /**
     * Method for deleting song by id
     * @param id Id of song to delete
     * @return Empty song with HTTP status of 204 or song with error if no song was found
     */
    @DeleteMapping("/delete")
    public SongDto deleteSong(@RequestParam("id") UUID id, HttpServletResponse httpServletResponse) {
        boolean deleted = restSongService.removeSong(id);

        SongDto songDto = null;
        if (!deleted) {
            songDto = new SongDto();
            songDto.setErrors(Collections.singletonList(new ObjectError("songDto",
                    new String[]{"song.notFound"}, null,
                    "No song found with such id.")));
        } else {
            httpServletResponse.setStatus(204);
        }

        return songDto;
    }

    @ModelAttribute
    public SongDto songDto() {
        return new SongDto();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(songDtoValidator);
    }
}
