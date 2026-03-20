package tari.socialsonic.utils.serializers;

import tari.socialsonic.SubsonicResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            if (!subsonicResponse.childNodes.isEmpty()) writeChildNodes(subsonicResponse,jsonGenerator,"root");
            xmlGenerator.writeEndObject();
        }
        else {
            jsonGenerator.writeStartObject();
            // This is here because we need a root node.
            jsonGenerator.writeName("subsonic-response");
            jsonGenerator.writeStartObject();

            writeAttributes(subsonicResponse, jsonGenerator);
            if (!subsonicResponse.childNodes.isEmpty()) writeChildNodes(subsonicResponse, jsonGenerator,"root");

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

                if (entry.getValue() instanceof String str) {
                    xmlGenerator.setNextIsAttribute(true);
                    xmlGenerator.writeName(entry.getKey());
                    xmlGenerator.writeString(str);
                }
                else if(entry.getValue() instanceof Boolean bool){
                    xmlGenerator.setNextIsAttribute(true);
                    xmlGenerator.writeName(entry.getKey());
                    xmlGenerator.writeBoolean(bool);
                }
                else if(entry.getValue() instanceof Number num){
                    xmlGenerator.setNextIsAttribute(true);
                    xmlGenerator.writeName(entry.getKey());
                    xmlGenerator.writeNumber((int) num);
                }
            }
            for (Map.Entry<String,Object> entry: response.attributes.entrySet()){
                // This second loop is required, as writing an array closes the object,
                // and if included in the loop above, would prevent other attributes from being written.
                // I might just be tired right now, but this seems like the easiest, albeit a costly solution.
                if (entry.getValue() instanceof int[] arr) {
                    xmlGenerator.setNextIsAttribute(false);
                    xmlGenerator.writeName(entry.getKey());
                    xmlGenerator.writeStartArray();
                    for (int i : arr) {
                        xmlGenerator.writeNumber(i);
                    }
                    xmlGenerator.writeEndArray();
                }
            }

        }
        else {
            for (Map.Entry<String,Object> entry: response.attributes.entrySet()){
                generator.writeName(entry.getKey());
                if (entry.getValue() instanceof int[] arr)
                    generator.writeArray(arr,0, arr.length);
                else if(entry.getValue() instanceof Integer num)
                    generator.writeNumber(num);
                else generator.writeString(entry.getValue().toString());
            }
        }
    }

    /**
     * Writes child nodes recursively for the specified object.
     * @param response the {@link SubsonicResponse} object to read child nodes from
     * @param generator the generator, either of type {@link JsonGenerator} or {@link ToXmlGenerator}.
     */
    private void writeChildNodes(SubsonicResponse response, JsonGenerator generator,String parentNodeName){
        Set<String> nodesAllowedToHaveMultipleChildren = new HashSet<>();
        nodesAllowedToHaveMultipleChildren.add("users");
        for (Map.Entry<String,List<SubsonicResponse>> children: response.childNodes.entrySet()){
            List<SubsonicResponse> childNodes = children.getValue();
            // hacky solution, not really sure why it worked first try,
            // and not sure how I could do it differently in a non-hacky way quite yet,
            // which means refactor soon TM I guess
            if(nodesAllowedToHaveMultipleChildren.contains(parentNodeName)){
                generator.writeArrayPropertyStart(children.getKey());
                for (SubsonicResponse child : childNodes){
                    writeChildObject(child,generator, children.getKey());
                }
                generator.writeEndArray();
            }
            else {
                generator.writeName(children.getKey());
                writeChildObject(childNodes.getFirst(),generator, children.getKey());
            }
        }
    }

    /**
     * Method to write a child object, to be used in a context where the object name already has been written, or in an
     * array context, such as in {@code writeChildNodes()} above.
     * @param response the response to be passed in from either a
     * @param generator the generator passed along, either of type {@link JsonGenerator}, or {@link ToXmlGenerator}
     */
    private void writeChildObject(SubsonicResponse response,JsonGenerator generator,String nodeName){
        generator.writeStartObject();
        writeAttributes(response,generator);
        if (!response.childNodes.isEmpty()) writeChildNodes(response,generator,nodeName);
        generator.writeEndObject();
    }

}
