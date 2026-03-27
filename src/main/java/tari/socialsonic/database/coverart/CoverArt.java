package tari.socialsonic.database.coverart;

import jakarta.persistence.*;

@Entity
public class CoverArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
