package ru.iteco.dao;

import ru.iteco.model.Album;
import ru.iteco.model.Song;

import java.util.UUID;

public interface AlbumDAO extends GenericDAO<UUID, Album> {

    Album getByName(String name);

    Album getBySong(Song song);
}
