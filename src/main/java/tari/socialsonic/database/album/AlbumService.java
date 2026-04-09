package tari.socialsonic.database.album;

import org.springframework.stereotype.Service;
import tari.socialsonic.database.song.Song;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    AlbumService(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
    }

    List<Song> getSongsByAlbumId(int id){
        return albumRepository.getAlbumById(id).songs;
    }
}
