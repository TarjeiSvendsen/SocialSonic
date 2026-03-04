package tari.socialsonic.database.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoles;
import tari.socialsonic.utils.auth.UserUtils;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTests")
class UserTests {

    @Nested
    class UserRoleTests{
        @Test
        public void adminRoleIsFalseOnCreation(){
            UserRoles roles = new UserRoles();
            assertFalse(roles.hasAdminRole());
        }
        @Test
        public void userRolesMatchDefaultWhenCreatedByUserUtilsWithEmptyParams(){
            UserRoles roles = UserUtils.setRoles(new HashMap<>(),false);
            assertFalse(roles.hasAdminRole());
            assertTrue(roles.hasStreamRole());
        }

    }

}