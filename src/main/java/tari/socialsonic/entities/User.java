package tari.socialsonic.entities;

import jakarta.persistence.*;
import org.springframework.util.DigestUtils;

import java.security.SecureRandom;

@Entity
@Table(name = "social_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String salt;
    private byte[] hashedPassword;
    
    public User(){
    }

    public User(String userName, String password){
        SecureRandom sr = new SecureRandom();
        this.salt = Long.toHexString(sr.nextLong());

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

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    // TODO, should make updating the password possible
    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

}
