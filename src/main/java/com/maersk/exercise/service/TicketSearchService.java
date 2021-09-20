package com.joha.exercise.service;

import com.google.gson.*;
import com.joha.exercise.domain.Ticket;
import com.joha.exercise.filehandling.ScheduledFileLoader;
import com.joha.exercise.service.datamapping.JsonDataMappingTicketSearchComponent;
import com.joha.exercise.service.stream.JsonStreamTicketSearchComponent;
import com.joha.exercise.service.stream.JsonStreamTicketSearchComponentAsync;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class TicketSearchService {

    private final ScheduledFileLoader scheduledFileLoader;
    private final JsonStreamTicketSearchComponent jsonStreamTicketSearchComponent;
    private final JsonDataMappingTicketSearchComponent jsonDataMappingTicketSearchComponent;
    private final JsonStreamTicketSearchComponentAsync jsonStreamTicketSearchComponentAsync;

    public TicketSearchService(ScheduledFileLoader scheduledFileLoader, JsonStreamTicketSearchComponent jsonStreamTicketSearchComponent, JsonDataMappingTicketSearchComponent jsonDataMappingTicketSearchComponent, JsonStreamTicketSearchComponentAsync jsonStreamTicketSearchComponentAsync) {
        this.scheduledFileLoader = scheduledFileLoader;
        this.jsonStreamTicketSearchComponent = jsonStreamTicketSearchComponent;
        this.jsonDataMappingTicketSearchComponent = jsonDataMappingTicketSearchComponent;
        this.jsonStreamTicketSearchComponentAsync = jsonStreamTicketSearchComponentAsync;
    }

    public JsonArray searchForTicketsByStreaming(String attribute, String value) throws IOException {
        BufferedReader bufferedReaderLatestJsonDataFile = scheduledFileLoader.getBufferedReaderLatestJsonDataFile();
        return jsonStreamTicketSearchComponent.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile);
    }

    public JsonArray searchForTicketsByStreamingBenchMarking(String attribute, String value) throws IOException {
        BufferedReader bufferedReaderLatestJsonDataFile = scheduledFileLoader.getBufferedReaderFromBenchmarkFile();
        return jsonStreamTicketSearchComponent.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile);
    }

    //This should be refactored
    public JsonArray searchForTicketsByStreamingAsync(String attribute, String value) throws IOException, ExecutionException, InterruptedException {
        System.out.println("Initial thread" + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
        long starttime = System.currentTimeMillis();
        BufferedReader bufferedReaderLatestJsonDataFile01 = scheduledFileLoader.getBufferedReaderLatestJsonDataFileByName("file1.json");
        BufferedReader bufferedReaderLatestJsonDataFile02 = scheduledFileLoader.getBufferedReaderLatestJsonDataFileByName("file2.json");
        BufferedReader bufferedReaderLatestJsonDataFile03 = scheduledFileLoader.getBufferedReaderLatestJsonDataFileByName("file3.json");
        BufferedReader bufferedReaderLatestJsonDataFile04 = scheduledFileLoader.getBufferedReaderLatestJsonDataFileByName("file4.json");
        BufferedReader bufferedReaderLatestJsonDataFile05 = scheduledFileLoader.getBufferedReaderLatestJsonDataFileByName("file5.json");
        long afterGettingFiles = System.currentTimeMillis() - starttime;
        starttime = System.currentTimeMillis();
        System.out.println("After files: " + afterGettingFiles);
        Future<JsonArray> futureForFile1 = jsonStreamTicketSearchComponentAsync.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile01);
        Future<JsonArray> futureForFile2 = jsonStreamTicketSearchComponentAsync.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile02);
        Future<JsonArray> futureForFile3 = jsonStreamTicketSearchComponentAsync.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile03);
        Future<JsonArray> futureForFile4 = jsonStreamTicketSearchComponentAsync.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile04);
        Future<JsonArray> futureForFile5 = jsonStreamTicketSearchComponentAsync.searchByUsingStream(attribute, value, bufferedReaderLatestJsonDataFile05);
        long afterFutures = System.currentTimeMillis() - starttime;
        System.out.println("After futures: " + afterFutures);
        starttime = System.currentTimeMillis();

        JsonArray jsonElementsOfFile1 = null;
        JsonArray jsonElementsOfFile2 = null;
        JsonArray jsonElementsOfFile3 = null;
        JsonArray jsonElementsOfFile4 = null;
        JsonArray jsonElementsOfFile5 = null;
        while(!futureForFile1.isDone() || jsonElementsOfFile1 == null ||
                !futureForFile2.isDone() || jsonElementsOfFile2 == null ||
                !futureForFile3.isDone() || jsonElementsOfFile3 == null ||
                !futureForFile4.isDone() || jsonElementsOfFile4 == null ||
                !futureForFile5.isDone() || jsonElementsOfFile5 == null
        ){
            if (futureForFile1.isDone()){
                jsonElementsOfFile1 = futureForFile1.get();
            }
            if (futureForFile2.isDone()){
                jsonElementsOfFile2 = futureForFile2.get();
            }
            if (futureForFile3.isDone()){
                jsonElementsOfFile3 = futureForFile3.get();
            }
            if (futureForFile4.isDone()){
                jsonElementsOfFile4 = futureForFile4.get();
            }
            if (futureForFile5.isDone()){
                jsonElementsOfFile5 = futureForFile5.get();
            }
        }
        long afterFinish = System.currentTimeMillis() - starttime;
        System.out.println("After futures finish: " + afterFinish);
        starttime = System.currentTimeMillis();

        jsonElementsOfFile1.addAll(jsonElementsOfFile2);
        jsonElementsOfFile1.addAll(jsonElementsOfFile3);
        jsonElementsOfFile1.addAll(jsonElementsOfFile4);
        jsonElementsOfFile1.addAll(jsonElementsOfFile5);

        long afterAdding = System.currentTimeMillis() - starttime;
        System.out.println("After adding: " + afterAdding);
        System.out.println("Ending thread" + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());

        return jsonElementsOfFile1;
    }

    public List<Ticket> searchForTicketsByByDataMapping(String attribute, String value) throws IOException {
        BufferedReader bufferedReaderLatestJsonDataFile = scheduledFileLoader.getBufferedReaderLatestJsonDataFile();
        return jsonDataMappingTicketSearchComponent.searchForValueUsingDataMapping(attribute, value, bufferedReaderLatestJsonDataFile);
    }

}

