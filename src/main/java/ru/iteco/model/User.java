package ru.iteco.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User implements Identifiable<UUID> {

    private UUID id;
    private String email;
    private String userName;
    private String passwordHash;
    private List<Song> favouriteSongs;
    private List<Artist> favouriteArtists;
    private List<Album> favouriteAlbums;

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) throws NoSuchAlgorithmException {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(passwordBytes);
        this.passwordHash = new String(digest, StandardCharsets.UTF_8);
    }

    public List<Song> getFavouriteSongs() {
        return favouriteSongs;
    }

    public void setFavouriteSongs(List<Song> favouriteSongs) {
        this.favouriteSongs = favouriteSongs;
    }

    public List<Artist> getFavouriteArtists() {
        return favouriteArtists;
    }

    public void setFavouriteArtists(List<Artist> favouriteArtists) {
        this.favouriteArtists = favouriteArtists;
    }

    public List<Album> getFavouriteAlbums() {
        return favouriteAlbums;
    }

    public void setFavouriteAlbums(List<Album> favouriteAlbums) {
        this.favouriteAlbums = favouriteAlbums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                email.equals(user.email) &&
                userName.equals(user.userName) &&
                passwordHash.equals(user.passwordHash) &&
                Objects.equals(favouriteSongs, user.favouriteSongs) &&
                Objects.equals(favouriteArtists, user.favouriteArtists) &&
                Objects.equals(favouriteAlbums, user.favouriteAlbums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, userName, passwordHash, favouriteSongs, favouriteArtists, favouriteAlbums);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
