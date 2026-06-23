package no.tari.socialsonic.library.album.recordlabel;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import no.tari.socialsonic.library.coverart.CoverArt;

import java.util.UUID;

/**
 * As per the Opensubsonic <a href="https://opensubsonic.netlify.app/docs/responses/recordlabel/">spec</a>
 *(with expansions by SocialSonic), a record label
 * contains a unique id, a name, and optional coverart
 * (although no plans for utilizing this specifically as of now).
 */
@Entity
public class RecordLabel {

    @Id
    private UUID id;
    private String name;
    @OneToOne
    @Nullable
    private CoverArt art;

}
