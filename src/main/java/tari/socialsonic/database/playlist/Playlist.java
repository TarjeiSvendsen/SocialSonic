package tari.socialsonic.database.playlist;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import tari.socialsonic.database.user.User;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "owner_fk"))
    @NotNull
    User owner;
    @OneToMany
    @JoinTable(name = "playlist_collaborators")
    List<User> collaborators;
    String title;
    String description;
    String imagePath;
    int visibility;
    @OneToMany
    @JoinTable(name = "playlist_songs")
    List<PlaylistSong> songs;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<User> collaborators) {
        this.collaborators = collaborators;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image_path) {
        this.imagePath = image_path;
    }

    public List<PlaylistSong> getSongs() {
        return songs;
    }

    public void setSongs(List<PlaylistSong> songs) {
        this.songs = songs;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
