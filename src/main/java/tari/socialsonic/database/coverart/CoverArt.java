package tari.socialsonic.database.coverart;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CoverArt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String path;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
