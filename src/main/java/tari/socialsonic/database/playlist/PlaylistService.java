package tari.socialsonic.database.playlist;

import org.springframework.stereotype.Service;
import tari.socialsonic.database.user.User;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    PlaylistService(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> getAllByUser(User user){
        return playlistRepository.getAllByOwner_Id(user.getId());
    }
    public Playlist getPlaylistById(int id){
        return playlistRepository.getPlaylistById(id);
    }
    public Playlist getFirstByTitle(String title){
        return playlistRepository.getFirstByTitle(title);
    }
    public List<Playlist> getAllByTitle(String title){
        return playlistRepository.getAllByTitle(title);
    }
    public List<Playlist> getAllByCollaborator(User user){
        return playlistRepository.getAllByCollaboratorsContains(user);
    }
    public Playlist save(Playlist playlist){
        return playlistRepository.save(playlist);
    }
}
