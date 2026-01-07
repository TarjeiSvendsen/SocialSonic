package tari.socialsonic.utils.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tari.socialsonic.SubsonicResponse;
import tari.socialsonic.utils.errors.ErrorCodes;

import java.util.Map;

@Component
public class ResponseUtils {

    ObjectMapper mapper = new ObjectMapper();
    XmlMapper xmlMapper = new XmlMapper();

    public ResponseUtils(){
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    public ResponseEntity<String> generateResponse(Map<String,String> params){
        SubsonicResponse response = new SubsonicResponse(true);
        return generateResponse(params,response);
    }
    public ResponseEntity<String> generateResponse(Map<String,String> params,SubsonicResponse plannedResponse){
        HttpHeaders headers = new HttpHeaders();
        if (!params.containsKey("f")) params.put("f","xml");
        try {
            if (params.get("f").equals("json")) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(mapper.writeValueAsString(plannedResponse), headers, HttpStatus.OK);
            } else if (params.get("f").equals("xml")) {
                headers.setContentType(MediaType.APPLICATION_XML);
                return new ResponseEntity<>(xmlMapper.writeValueAsString(plannedResponse), headers, HttpStatus.OK);
            } else
                return ResponseEntity.badRequest().body(mapper.writeValueAsString(ErrorCodes.createErrorResponseFromCode(0)));
        }
        catch (Exception e){
            // TODO, proper error handling required.
            System.out.println("Exception: " + e + " occurred.");
            return ResponseEntity.badRequest().body("Bad request");
        }
    }
}
