package no.tari.socialsonic.library.album;


import jakarta.persistence.*;
import no.tari.socialsonic.library.album.discs.DiscTitle;
import no.tari.socialsonic.library.album.genre.Genre;
import no.tari.socialsonic.library.album.recordlabel.RecordLabel;
import no.tari.socialsonic.library.artist.Artist;
import no.tari.socialsonic.library.coverart.CoverArt;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String sortName;
    private String version;
    private String artist;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artistId;
    private Date year;
    @OneToMany
    private List<CoverArt> coverArt;
    private int duration;
    private String genre;
    private Timestamp created;
    private int songCount;
    @ManyToMany
    @JoinTable(name = "album_record_labels",
            joinColumns = {@JoinColumn(name = "album_id",referencedColumnName = "id") },
            inverseJoinColumns = {@JoinColumn(name = "record_label_id",referencedColumnName = "id")}
              )
    private List<RecordLabel> recordLabels;
    private String musicBrainzId;
    @OneToMany
    private List<Genre> genres;
    @ManyToMany
    @JoinTable(name = "album_artists",
            joinColumns = {@JoinColumn(name = "album_id",referencedColumnName = "id") },
            inverseJoinColumns = {@JoinColumn(name = "artist_id",referencedColumnName = "id")}
    )
    private List<Artist> artists;
    //private String displayArtists;
    private List<String> releaseTypes; // Unsure if these should be their own entities or just strings.
    private List<String> moods; // --||--
    private Timestamp originalReleaseDate;
    private Timestamp releaseDate;
    private boolean isCompilation;
    private String explicitStatus;
    @ElementCollection
    private List<DiscTitle> discTitles;


}
