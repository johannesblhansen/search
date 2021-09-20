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

    public List<Ticket> searchForTicketsByByDataMapping(String attribute, String value) throws IOException {
        BufferedReader bufferedReaderLatestJsonDataFile = scheduledFileLoader.getBufferedReaderLatestJsonDataFile();
        return jsonDataMappingTicketSearchComponent.searchForValueUsingDataMapping(attribute, value, bufferedReaderLatestJsonDataFile);
    }

}

