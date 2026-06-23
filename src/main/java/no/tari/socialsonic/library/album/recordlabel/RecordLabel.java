package no.tari.socialsonic.library.album.recordlabel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import no.tari.socialsonic.library.coverart.CoverArt;

import java.util.UUID;

@Entity
public class RecordLabel {

    @Id
    private UUID id;
    private String name;
    @OneToOne
    private CoverArt art;

}
