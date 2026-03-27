package tari.socialsonic.database.stats.listens;

import jakarta.persistence.*;
import tari.socialsonic.database.song.Song;
import tari.socialsonic.database.user.User;

import java.sql.Timestamp;

@Entity
@Table(name = "listens")
public class Listen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    Song song;
    @ManyToOne
    User scrobbledBy;
    Timestamp dateScrobbled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getScrobbledBy() {
        return scrobbledBy;
    }

    public void setScrobbledBy(User scrobbledBy) {
        this.scrobbledBy = scrobbledBy;
    }

    public Timestamp getDateScrobbled() {
        return dateScrobbled;
    }

    public void setDateScrobbled(Timestamp dateScrobbled) {
        this.dateScrobbled = dateScrobbled;
    }
}
