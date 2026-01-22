package tari.socialsonic.database.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import tari.socialsonic.utils.auth.AuthenticationUtils;

import java.time.LocalDate;

@Entity
public class ApiKey {
    @Id
    private final String key;
    @ManyToOne
    @NotNull
    private User owner;
    private final LocalDate dateIssued;
    private final LocalDate dateValidTo;

    public ApiKey(){
        key = AuthenticationUtils.generateKey();
        dateIssued = LocalDate.now();
        dateValidTo = LocalDate.ofYearDay(LocalDate.now().getYear() +1, LocalDate.now().getDayOfYear());
    }
    public ApiKey(User user){
        this();
        this.owner = user;
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
