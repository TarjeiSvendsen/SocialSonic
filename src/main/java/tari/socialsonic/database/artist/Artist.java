package tari.socialsonic.database.artist;

import jakarta.persistence.*;


@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String coverArt;
    String artistImageUrl;
    int albumCount;
    String starred;
    String musicBrainzId;
    String sortName;
    String roles;

}
