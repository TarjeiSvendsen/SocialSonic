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

    /**
     *
     * @param subsonicResponse Value to serialize; can <b>not</b> be null.
     * @param jsonGenerator Generator used to output resulting JSON content
     * @param ctxt Context that can be used to get serializers for
     *   serializing Objects value contains, if any.
     * @throws JacksonException Handled automatically by Spring Boot
     */
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
            // This is here because we need a root node.
            jsonGenerator.writeName("subsonic-response");
            jsonGenerator.writeStartObject();

            writeAttributes(subsonicResponse, jsonGenerator);
            if (!subsonicResponse.childNodes.isEmpty()) writeChildNodes(subsonicResponse, jsonGenerator);

            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
        }
    }

    protected ResponseSerializer() {
        super();
    }

    /**
     * This writes the attributes from the subsonic objects attributes map.
     * @param response the {@link SubsonicResponse} object to read attributes from
     * @param generator the generator, either of type {@link JsonGenerator} or {@link ToXmlGenerator}.
     */
    private void writeAttributes(SubsonicResponse response,JsonGenerator generator){
        // Only really necessary in this method, as .setNextIsAttribute is specific to ToXmlGenerator.
        if (generator instanceof ToXmlGenerator xmlGenerator) {
            for (Map.Entry<String,Object> entry: response.attributes.entrySet()){
                // When writing attributes, writeStartObject is not necessary, as it is an attribute, not a new object.
                // Why I thought otherwise, idk...
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

    /**
     * Writes child nodes recursively for the specified object.
     * @param response the {@link SubsonicResponse} object to read child nodes from
     * @param generator the generator, either of type {@link JsonGenerator} or {@link ToXmlGenerator}.
     */
    private void writeChildNodes(SubsonicResponse response, JsonGenerator generator){
        // No need to differentiate between the generators in this, as this uses methods available in both.
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
