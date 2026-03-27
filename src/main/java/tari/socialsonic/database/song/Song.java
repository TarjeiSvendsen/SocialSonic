package tari.socialsonic.database.song;

import jakarta.persistence.*;
import tari.socialsonic.database.artist.Artist;
import tari.socialsonic.database.song.lyrics.Lyric;

import java.util.List;

/**
 * The DTO used to
 */
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "musicbrainz_id")
    String musicBrainzId;
    String title;
    String path;
    static final String type = "music";
    static final String mediaType = "song";
    String comment;
    String sortName;
    @OneToMany
    @JoinTable(name = "song_artists")
    List<Artist> artists;
    String displayArtists;
    String explicitStatus;
    int duration;
    int bpm;
    int bitrate;
    int bitDepth;
    int samplingRate;
    int channelCount;
    int year;
    int size;
    @OneToMany
    @JoinTable(name = "song_lyrics")
    List<Lyric> lyrics;

}
