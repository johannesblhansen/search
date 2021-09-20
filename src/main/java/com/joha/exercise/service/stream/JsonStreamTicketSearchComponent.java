package com.joha.exercise.service.stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonStreamTicketSearchComponent {

    private final JsonObjectExtractAndCollectComponent jsonObjectExtractAndCollectComponent;

    public JsonStreamTicketSearchComponent(JsonObjectExtractAndCollectComponent jsonObjectExtractAndCollectComponent) {
        this.jsonObjectExtractAndCollectComponent = jsonObjectExtractAndCollectComponent;
    }

    public JsonArray searchByUsingStream(String attribute, String value, BufferedReader bufferedReader) throws IOException {
        List<JsonObject> matchingJsonObjects = new ArrayList<>();
        JsonReader jsonReader = new JsonReader(bufferedReader);
        while (!jsonReader.peek().equals(JsonToken.END_DOCUMENT)) {
            JsonToken peek = jsonReader.peek();
            if (peek.equals(JsonToken.BEGIN_ARRAY)) {
                jsonReader.beginArray();
            } else if (peek.equals(JsonToken.BEGIN_OBJECT)) {
                jsonReader.beginObject();
                JsonObject jsonObject = jsonObjectExtractAndCollectComponent.handleJsonObject(jsonReader, attribute, value);
                if (jsonObject != null){
                    matchingJsonObjects.add(jsonObject);
                }
            } else if (peek.equals(JsonToken.END_OBJECT)) {
                jsonReader.endObject();
            } else if (peek.equals(JsonToken.END_ARRAY)) {
                jsonReader.endArray();
            }
        }
        JsonArray jsonArray = new JsonArray();
        matchingJsonObjects.forEach(jsonArray::add);
        return jsonArray;
    }
}
