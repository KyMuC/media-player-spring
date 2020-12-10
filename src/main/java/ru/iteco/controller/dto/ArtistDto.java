package ru.iteco.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Artist model data transfer object.
 *
 * @author Mikhail_Kudimov
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistDto {

    public ArtistDto() {
    }

    /**
     * Artist's identification.
     */
    private UUID id;

    /**
     * Artist's name.
     */
    private String name;

    /**
     * Artist's country of origin.
     */
    private String countryOfOrigin;

    /**
     * Ids of artist's songs.
     */
    private List<UUID> songIds;

    /**
     * Ids of artist's albums.
     */
    private List<UUID> albumIds;

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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<UUID> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<UUID> songIds) {
        this.songIds = songIds;
    }

    public List<UUID> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(List<UUID> albumIds) {
        this.albumIds = albumIds;
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
        ArtistDto artistDto = (ArtistDto) o;
        return Objects.equals(id, artistDto.id) &&
                Objects.equals(name, artistDto.name) &&
                Objects.equals(countryOfOrigin, artistDto.countryOfOrigin) &&
                Objects.equals(songIds, artistDto.songIds) &&
                Objects.equals(albumIds, artistDto.albumIds) &&
                Objects.equals(errors, artistDto.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryOfOrigin, songIds, albumIds, errors);
    }
}
