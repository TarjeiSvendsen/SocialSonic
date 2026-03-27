package tari.socialsonic.database.album;

import jakarta.persistence.*;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ID and MusicBrainz ID are separate, as an album could technically exist without being on musicbrainz.
    int id;
    int mb_id;
}
