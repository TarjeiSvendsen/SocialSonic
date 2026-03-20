package tari.socialsonic.utils.auth;

import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoles;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Objects;

public class UserUtils {



    /**
     * Sets the roles according to the ones provided with {@code params}.
     * @param params the parameters to parse, provided by a get, or post mapping in a controller.
     * @param isUserAdmin passed along to make sure no user gets roles they are not entitled to.
     * @return A string, containing the updated set of roles.
     */
    public static UserRoles setRoles(Map<String,String> params,boolean isUserAdmin){
        UserRoles tmpUserRoles = new UserRoles();
        for (String key: params.keySet()){
            switch (key){
                case "adminRole":
                    if (isUserAdmin) tmpUserRoles.setAdminRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "settingsRole":
                    tmpUserRoles.setSettingsRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "streamRole":
                    tmpUserRoles.setStreamRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "jukeboxRole":
                    tmpUserRoles.setJukeboxRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "downloadRole":
                    tmpUserRoles.setDownloadRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "uploadRole":
                    tmpUserRoles.setUploadRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "playlistRole":
                    tmpUserRoles.setPlaylistRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "coverArtRole":
                    tmpUserRoles.setCoverArtRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "commentRole":
                    tmpUserRoles.setCommentRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "podcastRole":
                    tmpUserRoles.setPodcastRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "shareRole":
                    tmpUserRoles.setShareRole(Boolean.parseBoolean(params.get(key)));
                    break;
                case "videoConversionRole":
                    tmpUserRoles.setVideoConversionRole(Boolean.parseBoolean(params.get(key)));
                    break;
                default:
                    break;
            }
        }

        return tmpUserRoles;
    }

    public static User handleUserParams(Map<String,String> params,User user){
        for (String key: params.keySet()){
            switch (key){
                case "username":
                    user.setUserName(params.get(key));
                    break;
                case "password":
                    user.setSalt(UserUtils.generateSalt());
                    user.setHashedPassword(PasswordUtils.hashPassword(user.getSalt(), params.get(key)));
                    break;
                case "email":
                    user.setEmail(params.get(key));
                    break;
                case "ldapAuthenticated":
                    // TODO, should be handled differently, as this can currently just be manually specified.
                    if (Objects.equals(params.get(key), "true"))
                        user.setLdapAuthenticated(true);
                    break;
                default:
                    break;
            }
        }
        return user;
    }
    /**
     * Checks if user has the administrator role.
     * @param user The {@link User} to check.
     * @return A boolean indicating whether the user has the admin role.
     */
    public static boolean isUserAdmin(User user){
        return user.getRoles().hasAdminRole();
    }

    public static byte[] generateSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] tmp = new byte[16];
        sr.nextBytes(tmp);
        return tmp;
    }

}
