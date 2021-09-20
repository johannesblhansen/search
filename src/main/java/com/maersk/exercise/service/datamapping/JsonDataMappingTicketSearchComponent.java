package com.joha.exercise.service.datamapping;

import com.google.gson.Gson;
import com.joha.exercise.domain.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JsonDataMappingTicketSearchComponent {

    private final ReflectionTicketFieldComparisonComponent reflectionTicketFieldComparisonComponent;

    public JsonDataMappingTicketSearchComponent(ReflectionTicketFieldComparisonComponent reflectionTicketFieldComparisonComponent) {
        this.reflectionTicketFieldComparisonComponent = reflectionTicketFieldComparisonComponent;
    }

    public List<Ticket> searchForValueUsingDataMapping(String attribute, String value, BufferedReader bufferedReader) {
        List<Ticket> matchingTickets = new ArrayList<>();
        Gson gson = new Gson();
        Ticket[] tickets = gson.fromJson(bufferedReader, Ticket[].class);
        Arrays.stream(tickets).forEach(ticket -> {
            reflectionTicketFieldComparisonComponent.checkAndCollectTicketThatMatchInput(attribute, value, ticket, matchingTickets);
        });
        return matchingTickets;
    }
}
