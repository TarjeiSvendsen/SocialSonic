package no.tari.socialsonic.library.album.genre;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Genre {

    @Id
    private int id;
    private String name;
}
