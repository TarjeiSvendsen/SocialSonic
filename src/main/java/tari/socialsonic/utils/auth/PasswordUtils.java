package tari.socialsonic.utils.auth;

import tari.socialsonic.database.user.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordUtils {
    /**
     * Hashes password using SHA-512 (TODO Will be bcrypt once refactor to spring security is done)
     * @param salt The salt to use.
     * @param passwordToHash the password to be hashed.
     * @return a byte array containing the hashed password.
     */
    public static byte[] hashPassword(byte[] salt, String passwordToHash){
        MessageDigest md;
        try {
            // TODO, refactor to Spring Security, so bcrypt can be utilized.
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Checks if a password is in clear text, as by the standard, encoded passwords should start with {@code enc: }
     * @return A boolean, yes if clear text, no if not.
     */
    public static boolean passwordIsClearText(String password){
        return !password.startsWith("enc:");
    }

    /**
     * Compares the password sent by the user, to the one stored in the database.
     * @param password The password sent in with a request
     * @param user the user object retrieved from the database.
     * @return a boolean indicating if the passwords match or not.
     */
    public static boolean comparePassword(String password, User user){
        byte[] sentPassword = hashPassword(user.getSalt(),password);
        return Arrays.equals(sentPassword, user.getHashedPassword());
    }

}
