package tari.socialsonic.utils.serializers;

import tari.socialsonic.SubsonicResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.util.List;
import java.util.Map;

public class ResponseSerializer extends ValueSerializer<SubsonicResponse> {

    @Override
    public void serialize(SubsonicResponse subsonicResponse, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
        if (jsonGenerator instanceof ToXmlGenerator xmlGenerator) {
            xmlGenerator.writeStartObject();
            writeAttributes(subsonicResponse,xmlGenerator);
            if (!subsonicResponse.childNodes.isEmpty()) writeChildNodes(subsonicResponse,jsonGenerator);
            xmlGenerator.writeEndObject();
        }
        else {
            jsonGenerator.writeStartObject();

            jsonGenerator.writeName("subsonic-response");
            jsonGenerator.writeStartObject();

            writeAttributes(subsonicResponse, jsonGenerator);
            writeChildNodes(subsonicResponse, jsonGenerator);

            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
        }
    }

    protected ResponseSerializer() {
        super();
    }

    private void writeAttributes(SubsonicResponse response,JsonGenerator generator){
        if (generator instanceof ToXmlGenerator xmlGenerator) {
            for (Map.Entry<String,Object> entry: response.attributes.entrySet()){
                xmlGenerator.writeName(entry.getKey());
                xmlGenerator.setNextIsAttribute(true);
                xmlGenerator.writeString(entry.getValue().toString());
            }
        }
        else {
            for (Map.Entry<String,Object> entry: response.attributes.entrySet()){
                generator.writeName(entry.getKey());
                generator.writeString(entry.getValue().toString());
            }
        }
    }
    private void writeChildNodes(SubsonicResponse response, JsonGenerator generator){
        for (Map.Entry<String,List<SubsonicResponse>> children: response.childNodes.entrySet()){
            for (SubsonicResponse child : children.getValue()){
                generator.writeName(children.getKey());
                generator.writeStartObject();
                writeAttributes(child,generator);
                if (!child.childNodes.isEmpty()) writeChildNodes(child,generator);
                generator.writeEndObject();
            }
        }
    }

}
