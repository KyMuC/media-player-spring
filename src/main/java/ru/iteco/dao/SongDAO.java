package ru.iteco.dao;

import ru.iteco.model.Song;

import java.util.UUID;

public interface SongDAO extends GenericDAO<UUID, Song> {

    Song getByName(String name);

}
