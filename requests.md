### Документация API

[Запросы для экспорта в Postman][1]

##### User API:
POST: http://localhost:8080/api/v1/user/

Пример тела запроса:

    {
        "userName": "kymuc",
        "password": "pass",
        "passwordConfirmation": "pass",
        "email": "kymuc@example.com"
    }

GET: http://localhost:8080/api/v1/user/<user_name_or_email>

<user_name_or_email> - логин пользователя или его email

PUT: http://localhost:8080/api/v1/user/<user_name_or_email>

Пример тела запроса:

    {
        "favouriteSongsIds": ["<song_id_1>", "<song_id_2>"]
    }

<user_name_or_email> - логин пользователя или его email

<song_id_n> - id существующей в системе песни

В теле запроса могут присутствовать любые поля [UserDto][2], за исключением id.

DELETE: http://localhost:8080/api/v1/user/<user_name_or_email>

<user_name_or_email> - логин пользователя или его email

##### Artist API:
POST: http://localhost:8080/api/v1/artist/

Пример тела запроса:

    {
        "name": "Vance Joy",
        "countryOfOrigin": "Australia"
    }

GET: http://localhost:8080/api/v1/artist/<artist_name>

<artist_name> - имя исполнителя

Возвращает список исполнителей с указанным именем

PUT: http://localhost:8080/api/v1/artist/<artist_id>

Пример тела запроса:

    {
        "id": "<artist_id>",
        "name": "Vance joy"
    }

<artist_id> - id исполнителя, которого необходимо обновить

В теле запроса могут присутствовать любые поля [ArtistDto][3].

DELETE: http://localhost:8080/api/v1/artist/<artist_id>

<artist_id> - id исполнителя, которого необходимо удалить

##### Song API:
POST: http://localhost:8080/api/v1/song/

Пример тела запроса:

    {
        "name": "Riptide",
        "artistId": "<artist_id>"
    }
    
<artist_id> - id существуюшего в системе исполнителя

GET: http://localhost:8080/api/v1/song/<song_name>

<song_name> - имя песни

Возвращает список песен с указанным именем

PUT: http://localhost:8080/api/v1/song/<song_id>

Пример тела запроса:

    {
        "id": "<song_id>",
        "name": "riptide"
    }
    
<song_id> - id песни, которую необходимо обновить

В теле запроса могут присутствовать любые поля [SongDto][4].

DELETE: http://localhost:8080/api/v1/song/<song_id>

<song_id> - id песни, которую необходимо удалить

##### Album API:

POST: http://localhost:8080/api/v1/album/

Пример тела запроса:

    {
        "name": "God Loves You When You're Dancing",
        "artistId": "<artist_id>",
        "songIds": ["<song_id>"]
    }
    
<artist_id> - id существующего в системе исполнителя

<song_id> - id существующей в системе песни

GET: http://localhost:8080/api/v1/album/<album_name>

<album_name> - имя альбома

Возвращает список альбомов с указанным именем

PUT: http://localhost:8080/api/v1/album/<album_id>

Пример тела запроса:

    {
        "id": "<album_id>",
        "name": "God Loves You When You Are Dancing"
    }
    
<album_id> - id альбома, который необходимо обновить

В теле запроса могут присутствовать любые поля [AlbumDto][5], за исключением artistId.

DELETE: http://localhost:8080/api/v1/album/<album_id>

<album_id> - id альбома, который необходимо удалить

[1]: requests.json
[2]: src/main/java/ru/iteco/controller/dto/UserDto.java
[3]: src/main/java/ru/iteco/controller/dto/ArtistDto.java
[4]: src/main/java/ru/iteco/controller/dto/SongDto.java
[5]: src/main/java/ru/iteco/controller/dto/AlbumDto.java