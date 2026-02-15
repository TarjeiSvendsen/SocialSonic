package tari.socialsonic.database.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.util.DigestUtils;

import java.security.SecureRandom;

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

    public User(String userName, String password){
        SecureRandom sr = new SecureRandom();
        this.salt = Long.toHexString(sr.nextLong());
        this.userName = userName;
        String tmpPass = password + this.salt;
        this.hashedPassword = DigestUtils.md5Digest(tmpPass.getBytes());

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
