package tari.socialsonic.utils.auth;

import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoles;
import tari.socialsonic.user.RoleFunction;

import java.security.SecureRandom;
import java.util.Map;

public class UserUtils {



    /**
     * Finds and alters a given role using the functional interface; {@link RoleFunction}.
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
     * Checks if user has the administrator role.
     * @param user The {@link User} to check.
     * @return A boolean indicating whether the user has the admin role.
     */
    public static boolean isUserAdmin(User user){
        return user.getRoles().hasAdminRole();
    }


}
