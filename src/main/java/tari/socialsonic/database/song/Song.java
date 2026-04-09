package tari.socialsonic.database.song;

import jakarta.persistence.*;
import tari.socialsonic.database.artist.Artist;
import tari.socialsonic.database.song.lyrics.Lyrics;
import tari.socialsonic.database.song.replaygain.ReplayGain;

import java.util.List;
import java.util.UUID;

/**
 * The Song Entity, containing (for now) most of the information specified by <a href="https://opensubsonic.netlify.app/docs/responses/child/">Child | OpenSubsonic</a>
 */
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(name = "musicbrainz_id")
    String musicBrainzId;
    String title;
    String path;
    static final String type = "music"; // Since these are
    static final String mediaType = "song";
    boolean isVideo = false;
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
    long size;
    @ElementCollection
    List<Lyrics> lyrics;

    @Embedded
    ReplayGain replayGain;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getDisplayArtists() {
        return displayArtists;
    }

    public void setDisplayArtists(String displayArtists) {
        this.displayArtists = displayArtists;
    }

    public String getExplicitStatus() {
        return explicitStatus;
    }

    public void setExplicitStatus(String explicitStatus) {
        this.explicitStatus = explicitStatus;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getBitDepth() {
        return bitDepth;
    }

    public void setBitDepth(int bitDepth) {
        this.bitDepth = bitDepth;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<Lyrics> getLyrics() {
        return lyrics;
    }

    public void setLyrics(List<Lyrics> lyrics) {
        this.lyrics = lyrics;
    }

    public ReplayGain getReplayGain() {
        return replayGain;
    }

    public void setReplayGain(ReplayGain replayGain) {
        this.replayGain = replayGain;
    }
}
