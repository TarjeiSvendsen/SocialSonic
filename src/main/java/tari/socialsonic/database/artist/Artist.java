package tari.socialsonic.database.artist;

import jakarta.persistence.*;
import tari.socialsonic.database.coverart.CoverArt;

import java.util.UUID;


@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String musicBrainzId;

    String name;

    @OneToOne
    @JoinColumn(name = "cover_art",foreignKey = @ForeignKey(name = "fk_artist_cover_art"))
    CoverArt coverArt;
    String artistImageUrl;

    String sortName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMusicBrainzId() {
        return musicBrainzId;
    }

    public void setMusicBrainzId(String musicBrainzId) {
        this.musicBrainzId = musicBrainzId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoverArt getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(CoverArt coverArt) {
        this.coverArt = coverArt;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String artistImageUrl) {
        this.artistImageUrl = artistImageUrl;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
