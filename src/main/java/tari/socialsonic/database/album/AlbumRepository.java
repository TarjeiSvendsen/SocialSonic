package tari.socialsonic.database.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tari.socialsonic.database.artist.Artist;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Integer> {

    Album getAlbumById(int id);

    List<Album> getAllByArtist(Artist artist);

}
