package tari.socialsonic.database.models;

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


    /**
     * Finds and alters a given role using the functional interface; {@link RoleFunction}.
     * @param role the role to be changed.
     * @param rf The function to call on the role.
     * @return An Integer, representing the new value, or -1 if it could not find the role.
     */
    private int findAndAlterRole(String role, RoleFunction rf){
        char[] tmpRoles = roles.toCharArray();
        int indexToModify;
        switch (role){
            case "adminRole":
                indexToModify = 0;
                break;
            case "settingsRole":
                indexToModify = 1;
                break;
            case "streamRole":
                indexToModify = 2;
                break;
            case "jukeboxRole":
                indexToModify = 3;
                break;
            case "downloadRole":
                indexToModify = 4;
                break;
            case "uploadRole":
                indexToModify = 5;
                break;
            case "playlistRole":
                indexToModify = 6;
                break;
            case "coverArtRole":
                indexToModify = 7;
                break;
            case "commentRole":
                indexToModify = 8;
                break;
            case "podcastRole":
                indexToModify = 9;
                break;
            case "shareRole":
                indexToModify = 10;
                break;
            case "videoConversionRole":
                indexToModify = 11;
                break;
            default:
                // Since we can't find the requested role, we return early.
                return -1;
        }

        rf.action(tmpRoles[indexToModify]);

        StringBuilder builder = new StringBuilder();
        for (char i: tmpRoles)
            builder.append(i);
        roles = builder.toString();
        return tmpRoles[indexToModify];
    }

    public boolean isLdapAuthenticated() {
        return ldapAuthenticated;
    }

    public void setLdapAuthenticated(boolean ldapAuthenticated) {
        this.ldapAuthenticated = ldapAuthenticated;
    }
}
@FunctionalInterface
interface RoleFunction{
    void action(char role);
}