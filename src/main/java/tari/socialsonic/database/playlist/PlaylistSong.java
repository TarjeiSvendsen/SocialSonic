package tari.socialsonic.database.playlist;

import jakarta.persistence.*;
import tari.socialsonic.database.song.Song;
import tari.socialsonic.database.user.User;

import java.sql.Timestamp;

@Entity
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "song_id",foreignKey=@ForeignKey(name="song_fk"))
    Song song;
    @OneToOne
    @JoinColumn(name = "user_id",foreignKey=@ForeignKey(name="added_by_user_fk"))
    User addedBy;
    Timestamp date_added;

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

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getDate_added() {
        return date_added;
    }

    public void setDate_added(Timestamp date_added) {
        this.date_added = date_added;
    }
}
