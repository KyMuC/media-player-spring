package ru.iteco.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Album model data transfer object.
 *
 * @author Mikhail_Kudimov
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {

    public AlbumDto() {
    }

    /**
     * Album's identification.
     */
    private UUID id;

    /**
     * Album's name.
     */
    private String name;

    /**
     * Artist's id to which the album relates.
     */
    private UUID artistId;

    /**
     * Ids of songs in the album.
     */
    private List<UUID> songIds = new ArrayList<>();

    /**
     * List of errors to report back to the user.
     */
    private List<ObjectError> errors;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }

    public List<UUID> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<UUID> songIds) {
        this.songIds = songIds;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumDto albumDto = (AlbumDto) o;
        return Objects.equals(id, albumDto.id) &&
                Objects.equals(name, albumDto.name) &&
                Objects.equals(artistId, albumDto.artistId) &&
                Objects.equals(songIds, albumDto.songIds) &&
                Objects.equals(errors, albumDto.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artistId, songIds, errors);
    }
}
