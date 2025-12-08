package tari.socialsonic;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@Tag("UnitTests")
@SpringBootTest
class SystemTests {


    @Nested
    class systemPingTest{
        @Test
        void pingNoValue(){

        }
        @Test
        void pingError(){
            ArrayList<SubsonicResponse> nodes = new ArrayList<>();
            nodes.add(new SubsonicResponse(new SubsonicResponse.Attribute("code","42")));
            //response.childNodes.put("error",nodes);
        }
    }
}
