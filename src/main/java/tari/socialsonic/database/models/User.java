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

    public String getRoles(){
        return roles;
    }

    /**
     * Gets the status of the requested user role
     * @param role The role to be found in the form of a String.
     * @return An Integer, 1 for role being active, o for inactive, and -1 for not found
     */
    public int getUserRoleStatus(String role){
        return findAndAlterRole(role,null);
    }

    /**
     * Activates the given role, does NOT check for permission to actually do so.
     * @param role The role to be changed.
     * @return A boolean representing the result, true for successfully activating the role,
     * false if it could not find the role.
     */
    public boolean activateRole(String role){
        return findAndAlterRole(role, (c) -> c = 1) != -1;
    }

    /**
     * Disables the requested role, does NOT check for permission to actually do so.
     * @param role The role to be changed.
     * @return A boolean representing the result, true for successfully activating the role,
     * false if it could not find the role.
     */
    public boolean disableRole(String role){
        return findAndAlterRole(role,(c) -> c = 0) != 0;
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