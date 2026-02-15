package tari.socialsonic.database.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tari.socialsonic.database.user.User;
import tari.socialsonic.utils.auth.UserUtils;

import static org.junit.jupiter.api.Assertions.*;
@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
class UserTests {

    @Nested
    class UserRoleTests{
        @Test
        public void switchRoleOutputsCorrect(){
            String expected = "011000000000";
            User testUser = new User();
            UserUtils.setRoleStatus(testUser,"adminRole",false);
            assertEquals(expected,testUser.getRoles());
        }

    }

}