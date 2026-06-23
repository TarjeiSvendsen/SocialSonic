package no.tari.socialsonic.library.album.discs;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import no.tari.socialsonic.library.coverart.CoverArt;

/**
 * As per the Opensubsonic <a href="https://opensubsonic.netlify.app/docs/responses/disctitle/">spec</a>,
 * contains a disc number, a title, and optional coverart.
 */
@Embeddable
public class DiscTitle {
    private int disc;
    private String title;
    @OneToOne
    @Nullable
    private CoverArt coverArt;
}
