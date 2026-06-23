package no.tari.socialsonic.library.album.discs;

import jakarta.persistence.*;
import no.tari.socialsonic.library.coverart.CoverArt;

@Embeddable
public class DiscTitle {
    private int disc;
    private String title;
    @OneToOne
    private CoverArt coverArt;
}
