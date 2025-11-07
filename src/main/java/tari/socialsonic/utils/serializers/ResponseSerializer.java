package tari.socialsonic.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import tari.socialsonic.SubsonicResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResponseSerializer extends StdSerializer<SubsonicResponse> {

    public ResponseSerializer(){
        this(null);
    }

    protected ResponseSerializer(Class<SubsonicResponse> t) {
        super(t);
    }

    @Override
    public void serialize(SubsonicResponse subsonicResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (jsonGenerator instanceof ToXmlGenerator xmlGenerator) {
            xmlGenerator.writeStartObject();
            writeObjectAttributes(xmlGenerator,subsonicResponse);
            serializeChildren(xmlGenerator,subsonicResponse);
            xmlGenerator.writeEndObject();
        }
        else {
            jsonGenerator.writeStartObject();
            writeObjectAttributes(jsonGenerator,subsonicResponse);
            serializeChildren(jsonGenerator,subsonicResponse);
            jsonGenerator.writeEndObject();
        }
    }

    public void serializeChildren(JsonGenerator generator, SubsonicResponse subsonicResponse){
        for (Map.Entry<String, List<SubsonicResponse>> childNodes : subsonicResponse.childNodes.entrySet()) {
            for (SubsonicResponse node : childNodes.getValue()){
                writeRecursiveChildren(generator,node,childNodes.getKey());
            }
        }
    }

    public static void writeObjectAttributes(JsonGenerator generator, SubsonicResponse subsonicResponse) throws IOException {
        if (generator instanceof ToXmlGenerator xmlGenerator) {
            for (Map.Entry<String, Object> attribute : subsonicResponse.attributes.entrySet()) {
                xmlGenerator.setNextIsAttribute(true);
                generator.writeObjectField(attribute.getKey(), attribute.getValue());
            }
        }
        else {
            for (Map.Entry<String, Object> attribute : subsonicResponse.attributes.entrySet()) {
                // This works, so DON'T use writeObjectField, as with SerializationFeature.WRAP_ROOT_VALUE it thinks the attributes are root values or something,
                // IDK... I'm too tired of this shit

                if (attribute.getValue() instanceof Boolean) {
                    generator.writeBooleanField(attribute.getKey(), (Boolean) attribute.getValue());
                } else generator.writeStringField(attribute.getKey(), (String) attribute.getValue());

            }
        }
    }

    private void writeRecursiveChildren(JsonGenerator generator, SubsonicResponse element,String nodeName)  {
        try{
            generator.writeFieldName(nodeName);
            generator.writeStartObject();
            writeObjectAttributes(generator, element);
            if (!element.childNodes.isEmpty()) {
                for (Map.Entry<String, List<SubsonicResponse>> childNodes : element.childNodes.entrySet()) {
                    for (SubsonicResponse node : childNodes.getValue()){
                        writeRecursiveChildren(generator,node,childNodes.getKey());
                    }
                }
            }
            generator.writeEndObject();
        }
        catch (IOException ioException){
            // TODO Proper error handling
            System.out.println("Encountered error");
        }

    }
}
