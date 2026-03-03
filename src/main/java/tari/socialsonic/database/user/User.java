package tari.socialsonic.database.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "social_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    @Email
    private String email;
    private String salt;
    private byte[] hashedPassword;
    private boolean ldapAuthenticated;
    private String roles = "011000000000";

    public User(){
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

    public String getSalt(){
        return salt;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    // TODO, should make updating the password possible
    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRoles(){
        return roles;
    }

    public void setRoles(String roles){
        this.roles = roles;
    }

    public boolean isLdapAuthenticated() {
        return ldapAuthenticated;
    }

    public void setLdapAuthenticated(boolean ldapAuthenticated) {
        this.ldapAuthenticated = ldapAuthenticated;
    }
}
