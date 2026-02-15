package tari.socialsonic.utils.auth;

import tari.socialsonic.database.user.User;
import tari.socialsonic.user.RoleFunction;

public class UserUtils {


    /**
     * Finds and alters a given role using the functional interface; {@link RoleFunction}.
     * @param user The {@link User} object to be altered
     * @param role the role to be changed.
     * @param rf The function to call on the role.
     * @return A string, containing the updated set of roles.
     */
    private static String findAndAlterRole(User user, String role,RoleFunction rf){
        return findAndAlterRole(user.getRoles(),role,rf);
    }
    /**
     * Finds and alters a given role using the functional interface; {@link RoleFunction}.
     * @param role the role to be changed.
     * @param rf The function to call on the role.
     * @return A string, containing the updated set of roles.
     */
    private static String findAndAlterRole(String roles,String role, RoleFunction rf){
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
                return roles;
        }

        rf.action(tmpRoles,indexToModify);

        StringBuilder builder = new StringBuilder();
        for (char i: tmpRoles)
            builder.append(i);
        roles = builder.toString();
        return roles;
    }

    /**
     * Changes the requested role to the requested status.
     * @param role The role to change,
     * @param status The status to change the role to.
     */
    public static void setRoleStatus(User user, String role, boolean status){
        user.setRoles(findAndAlterRole(user,role, (c,index) -> {
            if (status) {
                c[index] = '1';
            } else c[index] = '0';
            System.out.println(c);
        }));
    }

    /**
     * Gets the current status of a role.
     * @param user the {@link User} object to get roles from
     * @param role the role to get the current status of
     * @return a boolean.
     */
    public static boolean getRoleStatus(User user, String role){
        final boolean[] returnStatus = new boolean[1];
        findAndAlterRole(user,role,(c,index)-> {
            // This feels hacky, should probably be changed?
            returnStatus[0] = c[index] == '1';
        });
        return returnStatus[0];
    }

    /**
     * Checks if user has the administrator role.
     * @param user The {@link User} to check.
     * @return A boolean indicating whether the user has the admin role.
     */
    public static boolean isUserAdmin(User user){
        return getRoleStatus(user,"adminRole");
    }


}
