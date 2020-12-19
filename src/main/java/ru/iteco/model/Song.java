package ru.iteco.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Song object model.
 */
public class Song implements Identifiable<UUID> {

    /**
     * Song's identification.
     */
    private UUID id;

    /**
     * Song's name.
     */
    private String name;

    public Song() {
    }

    public Song(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id.equals(song.id) &&
                name.equals(song.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
