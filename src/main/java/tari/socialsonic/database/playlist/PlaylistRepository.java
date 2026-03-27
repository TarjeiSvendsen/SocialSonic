package tari.socialsonic.database.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import tari.socialsonic.database.user.User;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {
    List<Playlist> getAllByOwner_Id(int ownerId);
    Playlist getPlaylistById(int id);
    Playlist getFirstByTitle(String title);
    List<Playlist> getAllByTitle(String title);
    List<Playlist> getAllByCollaboratorsContains(User collaborator);
}
