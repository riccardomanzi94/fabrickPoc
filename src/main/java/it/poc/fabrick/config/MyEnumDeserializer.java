package it.poc.fabrick.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import it.poc.fabrick.model.dto.CustomType;

import java.io.IOException;
import java.util.stream.Stream;

public class MyEnumDeserializer extends JsonDeserializer<CustomType> {

    @Override
    public CustomType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").asText();
        return Stream.of(CustomType.values())
                .filter(enumValue -> enumValue.getValue().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("type "+type+" is not recognized"));
    }
}
