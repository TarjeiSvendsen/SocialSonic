package no.tari.socialsonic.library.album;


import jakarta.persistence.*;
import no.tari.socialsonic.library.album.discs.DiscTitle;
import no.tari.socialsonic.library.album.genre.Genre;
import no.tari.socialsonic.library.album.recordlabel.RecordLabel;
import no.tari.socialsonic.library.artist.Artist;
import no.tari.socialsonic.library.coverart.CoverArt;
import no.tari.socialsonic.library.song.Song;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * As per the OpenSubSonics spec for <a href="https://opensubsonic.netlify.app/docs/responses/albumid3withsongs/">AlbumID3WithSongs</a>
 * (not the standard AlbumID3, as I see no use in including it, as the -WithSongs also includes the same fields),
 * this entity contains information about an album, and its related entities, such as disc titles, record labels, etc.
 * The key difference being the user specific fields, such as starred, playCount and the likes are absent.
 * However, these are added in the AlbumDTO, my idea being that this entity can be cached (once I get around to doing that) separately and can be reused.
 * As such, only the user specific fields need to be fetched (alternatively can also be cached).
 */
@Entity
public class Album {

    @Id
    @Column(
            name = "id",
            updatable = false,
            nullable = false,
            columnDefinition = "UUID DEFAULT uuidv7()"
    )
    private UUID id;
    private String name;
    private String sortName;
    private String version;
    private String artist;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artistId;
    private int year;
    @OneToMany
    private List<CoverArt> coverArt;
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
    @ManyToMany
    @JoinTable(name = "album_songs",
            joinColumns = {@JoinColumn(name = "album_id",referencedColumnName = "id") },
            inverseJoinColumns = {@JoinColumn(name = "song_id",referencedColumnName = "id")}
    )
    private List<Song> songs;

    public Album(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<CoverArt> getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(List<CoverArt> coverArt) {
        this.coverArt = coverArt;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public List<RecordLabel> getRecordLabels() {
        return recordLabels;
    }

    public void setRecordLabels(List<RecordLabel> recordLabels) {
        this.recordLabels = recordLabels;
    }

    public String getMusicBrainzId() {
        return musicBrainzId;
    }

    public void setMusicBrainzId(String musicBrainzId) {
        this.musicBrainzId = musicBrainzId;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<String> getReleaseTypes() {
        return releaseTypes;
    }

    public void setReleaseTypes(List<String> releaseTypes) {
        this.releaseTypes = releaseTypes;
    }

    public List<String> getMoods() {
        return moods;
    }

    public void setMoods(List<String> moods) {
        this.moods = moods;
    }

    public Timestamp getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(Timestamp originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isCompilation() {
        return isCompilation;
    }

    public void setCompilation(boolean compilation) {
        isCompilation = compilation;
    }

    public String getExplicitStatus() {
        return explicitStatus;
    }

    public void setExplicitStatus(String explicitStatus) {
        this.explicitStatus = explicitStatus;
    }

    public List<DiscTitle> getDiscTitles() {
        return discTitles;
    }

    public void setDiscTitles(List<DiscTitle> discTitles) {
        this.discTitles = discTitles;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
