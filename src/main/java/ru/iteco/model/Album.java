package ru.iteco.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Album object model.
 */
public class Album implements Identifiable<UUID> {

    /**
     * Album's identification.
     */
    private UUID id;

    /**
     * Album's name.
     */
    private String name;

    /**
     * Songs present in the album.
     */
    private List<Song> songs;

    public Album() {
    }

    public Album(UUID id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id.equals(album.id) &&
                Objects.equals(name, album.name) &&
                Objects.equals(songs, album.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, songs);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", songs=" + songs +
                '}';
    }
}
