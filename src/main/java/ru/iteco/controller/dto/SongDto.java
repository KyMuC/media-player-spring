package ru.iteco.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Song model data transfer object.
 *
 * @author Mikhail_Kudimov
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDto {

    public SongDto() {
    }

    /**
     * Song's identification.
     */
    private UUID id;

    /**
     * Song's name.
     */
    private String name;

    /**
     * Album's id if the song is a part of the album.
     */
    private UUID albumId;

    /**
     * Artist's id to which a song relates.
     */
    private UUID artistId;

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

    public UUID getAlbumId() {
        return albumId;
    }

    public void setAlbumId(UUID albumId) {
        this.albumId = albumId;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
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
        SongDto songDto = (SongDto) o;
        return Objects.equals(id, songDto.id) &&
                Objects.equals(name, songDto.name) &&
                Objects.equals(albumId, songDto.albumId) &&
                Objects.equals(artistId, songDto.artistId) &&
                Objects.equals(errors, songDto.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, albumId, artistId, errors);
    }
}
