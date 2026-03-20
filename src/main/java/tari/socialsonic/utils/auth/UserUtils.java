package tari.socialsonic.utils.auth;

import tari.socialsonic.SubsonicResponse;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoles;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Goes through the supplied params and alters a supplied user according to the params.
     * @param params the params to go through
     * @param user the user to modify
     * @return a modified user.
     */
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
     * Converts a given user to a subsonic response as defined in specification, excluding folders for now.
     * @param user the user to convert
     * @return a subsonic response representing the user.
     */
    public static SubsonicResponse convertUserToSubsonicResponse(User user){
        SubsonicResponse childNode = new SubsonicResponse();
        childNode.addAttribute(new SubsonicResponse.Attribute("username",user.getUserName()));
        childNode.addAttribute(new SubsonicResponse.Attribute("email",user.getEmail()));
        // TODO, remember changing this when implementing folder access.
        // childNode.addAttribute(new SubsonicResponse.Attribute("folder",new int[]{1,2}));
        for (SubsonicResponse.Attribute at: convertUserRolesToList(user)){
            childNode.addAttribute(at);
        }
        return childNode;
    }

    /**
     * Goes through the roles of a user and adds them to a list
     * @param user the user to get the roles from
     * @return a list containing the roles and their value in {@link tari.socialsonic.SubsonicResponse.Attribute}
     */
    public static List<SubsonicResponse.Attribute> convertUserRolesToList(User user){
        List<SubsonicResponse.Attribute> attributes = new ArrayList<>();
        UserRoles roles = user.getRoles();
        attributes.add(new SubsonicResponse.Attribute("adminRole",roles.hasAdminRole()));
        attributes.add(new SubsonicResponse.Attribute("commentRole",roles.hasCommentRole()));
        attributes.add(new SubsonicResponse.Attribute("downloadRole",roles.hasDownloadRole()));
        attributes.add(new SubsonicResponse.Attribute("jukeboxRole",roles.hasJukeboxRole()));
        attributes.add(new SubsonicResponse.Attribute("playlistRole",roles.hasPlaylistRole()));
        attributes.add(new SubsonicResponse.Attribute("podcastRole",roles.hasPodcastRole()));
        attributes.add(new SubsonicResponse.Attribute("settingRole",roles.hasSettingsRole()));
        attributes.add(new SubsonicResponse.Attribute("shareRole",roles.hasShareRole()));
        attributes.add(new SubsonicResponse.Attribute("uploadRole",roles.hasUploadRole()));
        attributes.add(new SubsonicResponse.Attribute("streamRole",roles.hasStreamRole()));
        attributes.add(new SubsonicResponse.Attribute("coverArtRole",roles.hasCoverArtRole()));
        return attributes;
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
