package ru.iteco.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Artist object model.
 */
public class Artist implements Identifiable<UUID> {

    private UUID id;
    private String name;
    private String countryOfOrigin;
    private List<Song> songs;
    private List<Album> albums;

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id.equals(artist.id) &&
                name.equals(artist.name) &&
                Objects.equals(countryOfOrigin, artist.countryOfOrigin) &&
                Objects.equals(songs, artist.songs) &&
                Objects.equals(albums, artist.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryOfOrigin, songs, albums);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", songs=" + songs +
                ", albums=" + albums +
                '}';
    }
}
