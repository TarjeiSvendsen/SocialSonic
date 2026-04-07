package tari.socialsonic.database.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import tari.socialsonic.database.user.roles.UserRoles;

@Entity
@Table(name = "social_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
    private byte[] salt;
    @NotNull
    private byte[] hashedPassword;
    private boolean ldapAuthenticated;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserRoles roles;

    public User(){
        this.roles = new UserRoles();
    }
    public User(UserRoles roles){
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getSalt(){
        return salt;
    }

    public void setSalt(byte[] salt){
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public UserRoles getRoles(){
        return roles;
    }

    public void setRoles(UserRoles roles){
        this.roles = roles;
    }

    public boolean isLdapAuthenticated() {
        return ldapAuthenticated;
    }

    public void setLdapAuthenticated(boolean ldapAuthenticated) {
        this.ldapAuthenticated = ldapAuthenticated;
    }
}
