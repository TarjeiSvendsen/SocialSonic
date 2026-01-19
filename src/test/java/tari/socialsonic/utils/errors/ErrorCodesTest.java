package tari.socialsonic.utils.errors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tari.socialsonic.SubsonicResponse;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodesTest {
    @Nested
    class ErrorCodeConstruction{

        @Test
        void doesNotThrowErrorOnInvalidInput(){
            assertNotNull(ErrorCodes.createErrorResponseFromCode(-1));
        }
        @Test
        void createsCorrectErrorCode(){
            SubsonicResponse errorResponse = ErrorCodes.createErrorResponseFromCode(70);
            assertEquals(70,errorResponse.childNodes.getFirst("error").attributes.get("code"));
        }
        @Test
        void ErrorRootObjectHasCorrectStatus(){
            SubsonicResponse response = ErrorCodes.createErrorResponseFromCode(0);
            assertEquals("failed", response.attributes.get("status"));
        }
        @Test
        void ErrorChildObjectDoesNotHaveStatusField(){
            SubsonicResponse response = ErrorCodes.createErrorResponseFromCode(0);
            SubsonicResponse childNode = response.childNodes.get("error").getFirst();
            assertNull(childNode.attributes.get("status"));
        }
        @Test
        void ErrorChildObjectHasCorrectAttributes(){
            SubsonicResponse response = ErrorCodes.createErrorResponseFromCode(0);
            SubsonicResponse childNode = response.childNodes.get("error").getFirst();
            assertTrue(childNode.attributes.containsKey("code"));
            assertTrue(childNode.attributes.containsKey("message"));
            assertTrue(childNode.attributes.containsKey("helpUrl"));

        }
    }
}