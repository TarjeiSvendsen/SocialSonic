package tari.socialsonic.database.album;

import jakarta.persistence.*;
import tari.socialsonic.database.album.recordLabels.RecordLabel;
import tari.socialsonic.database.artist.Artist;
import tari.socialsonic.database.coverart.CoverArt;
import tari.socialsonic.database.song.Song;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ID and MusicBrainz ID are separate, as an album could technically exist without being on musicbrainz.
    String musicBrainzId;

    String name;
    String sortName;

    @ManyToOne
    @JoinColumn(name = "main_artist",foreignKey = @ForeignKey(name = "fk_main_album_artist"))
    Artist artist;

    @OneToMany
    @JoinTable(name = "album_artists")
    List<Artist> artists;

    String version;

    String explicitStatus;

    boolean isCompilation;

    @OneToOne
    @JoinColumn(name="cover_art_id",foreignKey = @ForeignKey(name = "fk_album_cover_art"))
    CoverArt coverArt;

    Timestamp dateCreated;

    @OneToMany
    @JoinTable(name = "album_record_labels")
    List<RecordLabel> recordLabels;

    Date originalReleaseDate;
    Date releaseDate;

    @OneToMany
    @JoinTable(name = "album_songs")
    List<Song> songs;


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

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExplicitStatus() {
        return explicitStatus;
    }

    public void setExplicitStatus(String explicitStatus) {
        this.explicitStatus = explicitStatus;
    }

    public boolean isCompilation() {
        return isCompilation;
    }

    public void setIsCompilation(boolean compilation) {
        isCompilation = compilation;
    }

    public CoverArt getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(CoverArt coverArt) {
        this.coverArt = coverArt;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<RecordLabel> getRecordLabels() {
        return recordLabels;
    }

    public void setRecordLabels(List<RecordLabel> recordLabels) {
        this.recordLabels = recordLabels;
    }

    public Date getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(Date originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
