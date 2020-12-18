package ru.iteco.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User model data transfer object.
 *
 * @author Mikhail_Kudimov
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public UserDto() {
    }

    /**
     * User's identification
     */
    private UUID id;

    /**
     * User's user name.
     */
    private String userName;

    /**
     * User's password, prompted on authentication.
     */
    private String password;

    /**
     * User's password confirmation, prompted on authentication.
     */
    private String passwordConfirmation;

    /**
     * Hash of user's password.
     */
    private String passwordHash;

    /**
     * User's email.
     */
    private String email;

    /**
     * Ids of user's favourite songs.
     */
    private List<UUID> favouriteSongsIds = new ArrayList<>();

    /**
     * Ids of user's favourite artists.
     */
    private List<UUID> favouriteArtistsIds = new ArrayList<>();

    /**
     * Ids of user's favourite albums.
     */
    private List<UUID> favouriteAlbumsIds = new ArrayList<>();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UUID> getFavouriteSongsIds() {
        return favouriteSongsIds;
    }

    public void setFavouriteSongsIds(List<UUID> favouriteSongsIds) {
        this.favouriteSongsIds = favouriteSongsIds;
    }

    public List<UUID> getFavouriteArtistsIds() {
        return favouriteArtistsIds;
    }

    public void setFavouriteArtistsIds(List<UUID> favouriteArtistsIds) {
        this.favouriteArtistsIds = favouriteArtistsIds;
    }

    public List<UUID> getFavouriteAlbumsIds() {
        return favouriteAlbumsIds;
    }

    public void setFavouriteAlbumsIds(List<UUID> favouriteAlbumsIds) {
        this.favouriteAlbumsIds = favouriteAlbumsIds;
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
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(userName, userDto.userName) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(passwordConfirmation, userDto.passwordConfirmation) &&
                Objects.equals(passwordHash, userDto.passwordHash) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(favouriteSongsIds, userDto.favouriteSongsIds) &&
                Objects.equals(favouriteArtistsIds, userDto.favouriteArtistsIds) &&
                Objects.equals(favouriteAlbumsIds, userDto.favouriteAlbumsIds) &&
                Objects.equals(errors, userDto.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, passwordConfirmation, passwordHash, email, favouriteSongsIds, favouriteArtistsIds, favouriteAlbumsIds, errors);
    }
}
