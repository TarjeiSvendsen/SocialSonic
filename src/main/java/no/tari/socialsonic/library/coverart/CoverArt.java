package no.tari.socialsonic.library.coverart;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CoverArt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String filePath;
}
