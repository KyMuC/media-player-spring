package ru.iteco.dao;

import ru.iteco.model.Album;
import ru.iteco.model.Artist;
import ru.iteco.model.Song;

import java.util.UUID;

public interface ArtistDAO extends GenericDAO<UUID, Artist> {

    Artist getByName(String name);

    Artist getBySong(Song song);

    Artist getByAlbum(Album album);
}
