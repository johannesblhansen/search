package com.joha.exercise.service.stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonObjectExtractAndCollectComponent {

    public JsonObject handleJsonObject(JsonReader jsonReader, String attribute, String value) throws IOException {
        boolean attributeMatch = false;
        boolean valueMatch = false;
        JsonObject jsonObject = new JsonObject();
        String name = "";
        JsonElement jsonElement;
        while (!jsonReader.peek().equals(JsonToken.END_OBJECT)) {
            JsonToken peek = jsonReader.peek();
            if (peek.equals(JsonToken.NAME)) {
                name = jsonReader.nextName();
                attributeMatch = attribute.equals(name);
            } else if (peek.equals(JsonToken.NUMBER)) {
                jsonElement = JsonParser.parseReader(jsonReader);
                jsonObject.add(name, jsonElement);

                if (attributeMatch && jsonElement.toString().equals(value)) {
                    valueMatch = true;
                }
            } else if (peek.equals(JsonToken.STRING)) {
                jsonElement = JsonParser.parseReader(jsonReader);
                jsonObject.add(name, jsonElement);
                if (attributeMatch && jsonElement.getAsString().equals(value)) {
                    valueMatch = true;
                }
            } else if (peek.equals(JsonToken.BOOLEAN)) {
                jsonElement = JsonParser.parseReader(jsonReader);
                jsonObject.add(name, jsonElement);
                if (attributeMatch && jsonElement.toString().equals(Boolean.valueOf(value))) {
                    valueMatch = true;
                }
            } else if (peek.equals(JsonToken.NULL)) {
                jsonReader.skipValue();
            }
        }
        if (valueMatch) {
            return jsonObject;
        } else {
            return null;
        }
    }
}
