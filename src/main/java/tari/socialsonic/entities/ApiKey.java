package tari.socialsonic.entities;

import jakarta.persistence.*;
import tari.socialsonic.utils.auth.AuthenticationUtils;

import java.time.LocalDate;

@Entity
public class ApiKey {
    @Id
    private final String key;
    @ManyToOne
    private User issuedBy;
    private final LocalDate dateIssued;
    private final LocalDate dateValidTo;

    public ApiKey(){
        key = AuthenticationUtils.generateApiKey();
        dateIssued = LocalDate.now();
        dateValidTo = LocalDate.ofYearDay(LocalDate.now().getYear() +1, LocalDate.now().getDayOfYear());
    }
    public ApiKey(User user){
        this();
        this.issuedBy = user;
    }

    public boolean valid() {
        return LocalDate.now().isBefore(dateValidTo);
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }
    public String getKey() {
        return key;
    }
}
