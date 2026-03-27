package tari.socialsonic.database.song.lyrics;

import jakarta.persistence.*;
import tari.socialsonic.user.language.TwoLetterLanguageCodes;

import java.sql.Timestamp;

@Entity
public class Lyric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    TwoLetterLanguageCodes languageCode;
    String type;
    String lyrics; // TODO, should probably be a list of lyric-parts or something similar, but idk, i'm tired.
    String source;
    Timestamp dateCreated;
}
