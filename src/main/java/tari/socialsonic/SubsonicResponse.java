package tari.socialsonic;


import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import tari.socialsonic.utils.serializers.ResponseSerializer;

import java.util.HashMap;
import java.util.Map;
@JsonRootName(value = "subsonic-response")
@JacksonXmlRootElement(localName = "subsonic-response")
@JsonSerialize(using = ResponseSerializer.class)
public class SubsonicResponse {
    public boolean isRoot;
    public MultiValueMap<String,SubsonicResponse> childNodes;
    public Map<String, Object> attributes;


    /**
     * This constructs an empty root subsonic-response node
     */
    public SubsonicResponse(){
        this(true);
    }

    /**
     * This constructs an empty root or regular subsonic-response node.
     * @param isRoot determines if node is a root node or not, bad practice.
     */
    public SubsonicResponse(boolean isRoot){
        // TODO, this should not be manually determined, should be a check in the serializer instead, to make sure we don't nest root elements by accident.
        this.isRoot = isRoot;
        this.attributes = new HashMap<>();
        this.childNodes = new LinkedMultiValueMap<>();
        if (this.isRoot)addRootAttributes();
    }

    public SubsonicResponse(SubsonicResponse.Attribute... attributes){
        this.isRoot = false;
        this.attributes = new HashMap<>();
        this.childNodes = new LinkedMultiValueMap<>();
        for (Attribute a: attributes){
            this.attributes.put(a.key,a.value);
        }
    }

    public void addAttribute(Attribute attribute){
        attributes.put(attribute.key,attribute.value);
    }
    public void addChildNode(String elementName, SubsonicResponse childNode){
        childNodes.add(elementName, childNode);
    }

    private void addRootAttributes(){
        attributes.put("status","ok");
        attributes.put("version","1.15.1");
        attributes.put("type","SocialSonic-DEV");
        attributes.put("serverVersion","0.0.1 (Alotta Fagina)");
        attributes.put("openSubSonic",true);
    }

    public record Attribute(String key, Object value) {
    }
}

