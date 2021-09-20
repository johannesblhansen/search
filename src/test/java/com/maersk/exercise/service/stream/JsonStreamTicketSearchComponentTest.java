package com.joha.exercise.service.stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonStreamTicketSearchComponentTest {

    @Mock
    JsonObjectExtractAndCollectComponent jsonObjectExtractAndCollectComponent;

    @InjectMocks
    JsonStreamTicketSearchComponent jsonStreamTicketSearchComponent;

    @Test
    void testAJsonObjectIsAtEndWhenSeachIsCompletet() throws IOException {
        ArgumentCaptor<JsonReader> argumentCaptor = ArgumentCaptor.forClass(JsonReader.class);
        when(jsonObjectExtractAndCollectComponent.handleJsonObject(argumentCaptor.capture(), anyString(), anyString())).thenReturn(null);
        BufferedReader bufferedReader = new BufferedReader(new StringReader(jsonObjectString()));
        jsonStreamTicketSearchComponent.searchByUsingStream("attribute", "value", bufferedReader);
        JsonReader inputReader = argumentCaptor.getValue();
        assertEquals(JsonToken.END_DOCUMENT, inputReader.peek());
    }

    @Test
    void testJsonObjectIsAddedCorrectlyToJsonArray() throws IOException {
        JsonObject jsonObject = createJsonObject();
        when(jsonObjectExtractAndCollectComponent.handleJsonObject(any(), anyString(), anyString())).thenReturn(jsonObject);
        BufferedReader bufferedReader = new BufferedReader(new StringReader(jsonObjectString()));
        JsonArray jsonElements = jsonStreamTicketSearchComponent.searchByUsingStream("attribute", "value", bufferedReader);
        JsonObject jsonElement = (JsonObject)jsonElements.get(0);
        assertEquals(1L, jsonElement.get("id").getAsLong());
    }

    private JsonObject createJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", 1L);
        return jsonObject;
    }

    private String jsonObjectString(){
        JsonObject jsonObject = new JsonObject();
        return jsonObject.toString();
    }
}