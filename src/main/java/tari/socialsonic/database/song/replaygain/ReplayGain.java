package tari.socialsonic.database.song.replaygain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReplayGain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int trackGain;
    int albumGain;
    int trackPeak;
    int albumPeak;
    int baseGain;
    int fallbackGain;
}
