package com.joha.exercise.service.stream;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectExtractAndCollectComponentTest {

    private JsonObjectExtractAndCollectComponent unitUnderTest;


    @BeforeEach
    private void init(){
        unitUnderTest = new JsonObjectExtractAndCollectComponent();
    }

    @Test
    void handleFindingIdWithCorrectValue() throws IOException {
        String jsonString = jsonObjectString();
        StringReader stringReader = new StringReader(jsonString);
        JsonReader jsonReader = new JsonReader(stringReader);
        jsonReader.beginObject();
        String valueToTest = "1";
        String attribute = "id";
        JsonObject jsonObject = unitUnderTest.handleJsonObject(jsonReader, attribute, valueToTest);
        assertEquals(valueToTest, jsonObject.get(attribute).toString());
    }

    @Test
    void handleNotFindingIdWithIncorrectValue() throws IOException {
        String jsonString = jsonObjectString();
        StringReader stringReader = new StringReader(jsonString);
        JsonReader jsonReader = new JsonReader(stringReader);
        jsonReader.beginObject();
        String attribute = "id";
        String valueToTest = "5";
        JsonObject jsonObject = unitUnderTest.handleJsonObject(jsonReader, attribute, valueToTest);
        assertNull(jsonObject);
    }

    @Test
    void handleNotPrirityWithIncorrectValue() throws IOException {
        String jsonString = jsonObjectString();
        StringReader stringReader = new StringReader(jsonString);
        JsonReader jsonReader = new JsonReader(stringReader);
        jsonReader.beginObject();
        String attribute = "priority";
        String valueToTest = "high";
        JsonObject jsonObject = unitUnderTest.handleJsonObject(jsonReader, attribute, valueToTest);
        assertEquals(valueToTest, jsonObject.get(attribute).getAsString());
    }

    @Test
    void handleCallingWithNothing() throws IOException {
        String jsonString = jsonObjectString();
        StringReader stringReader = new StringReader(jsonString);
        JsonReader jsonReader = new JsonReader(stringReader);
        jsonReader.beginObject();
        String attribute = "";
        String valueToTest = "";
        JsonObject jsonObject = unitUnderTest.handleJsonObject(jsonReader, attribute, valueToTest);
        assertNull(jsonObject);
    }

    private String jsonObjectString(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", 1L);
        jsonObject.addProperty("type", "incident");
        jsonObject.addProperty("subject", "Booking Error");
        jsonObject.addProperty("description", "Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.");
        jsonObject.addProperty("priority", "high");
        jsonObject.addProperty("status", "pending");
        return jsonObject.toString();
    }
}