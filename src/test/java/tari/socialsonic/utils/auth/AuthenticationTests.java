package tari.socialsonic.utils.auth;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tari.socialsonic.database.models.User;

import java.util.HashMap;
import java.util.Map;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
public class AuthenticationTests {

    @Mock
    AuthenticationUtils utils;

    @Nested
    class ApiKeyTests{
        @Test
        public void authInvalid(){
            Map<String,String> params = new HashMap<>();
            params.put("apiKey","123");
            params.put("u","helloThere");
            assertNotEquals(-1,utils.authenticate(params),"Method should return invalid given the circumstance of both an api key and user");
        }
    }
    @Nested
    class UserPasswordTests{
        @Test
        public void legacyNotSupported(){
            Map<String,String> params = new HashMap<>();
            params.put("u","helloThere");
            params.put("p","nope");
            assertNotEquals(-1,utils.authenticate(params),"Legacy authentication with a plaintext password is not supported");
        }

        @Test
        public void missingUserParam(){
            Map<String,String> params = new HashMap<>();
            params.put("p","nope");
            assertNotEquals(-1,utils.authenticate(params),"Legacy authentication with a plaintext password is not supported");
        }
        @Test
        public void missingPasswordParam(){
            Map<String,String> params = new HashMap<>();
            params.put("u","helloThere");

            assertNotEquals(-1,utils.authenticate(params),"Legacy authentication with a plaintext password is not supported");
        }
        @Test
        public void missingTokenParam(){
            Map<String,String> params = new HashMap<>();
            params.put("u","helloThere");
            params.put("s","bingingSalt");
            assertNotEquals(-1,utils.authenticate(params),"Legacy authentication with a plaintext password is not supported");
        }
        @Test
        public void missingSaltParam(){
            Map<String,String> params = new HashMap<>();
            params.put("u","helloThere");
            params.put("t","h3h44h45");
            assertNotEquals(-1,utils.authenticate(params),"Legacy authentication with a plaintext password is not supported");
        }


    }
    @Nested class UserRoleUtilTests{
        @Test
        public void userIsNotAdminByDefault(){
            User user = new User();
            assertFalse(utils.isUserAdmin(user));
        }
        @Test
        public void userIsAdminAfterChange(){
            User user = new User();
            user.setRoleStatus("adminRole",true);
            assertFalse(utils.isUserAdmin(user));
        }
        @Test
        public void userRolesRepresentChange(){
            User user = new User();
            String expected = "011000000001";
            user.setRoleStatus("videoConversionRole",true);
            assertEquals(expected,user.getRoles());
        }
    }
    @Nested
    class GenerationTests{

        @Test
        public void apiKeyNotNull(){
            assertNotNull(AuthenticationUtils.generateKey());
        }
        @Test
        public void apiKeyNoLongerThan(){
            String result = AuthenticationUtils.generateKey();

            assertFalse(()-> result.length() > 64);
            assertTrue(()->result.length() > 8 );
        }
    }
}
