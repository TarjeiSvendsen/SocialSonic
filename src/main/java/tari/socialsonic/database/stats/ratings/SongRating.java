package tari.socialsonic.database.stats.ratings;


import jakarta.persistence.*;
import tari.socialsonic.database.song.Song;
import tari.socialsonic.database.user.User;

import java.sql.Timestamp;

@Entity
@Table(name = "song_ratings")
public class SongRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int rating;
    @ManyToOne
    Song song;
    @ManyToOne
    User ratedBy;
    Timestamp timeRated;

    public Timestamp getTimeRated() {
        return timeRated;
    }

    public void setTimeRated(Timestamp timeRated) {
        this.timeRated = timeRated;
    }

    public User getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(User ratedBy) {
        this.ratedBy = ratedBy;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
