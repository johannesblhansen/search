package com.joha.exercise.controllers;

import com.joha.exercise.domain.Ticket;
import com.joha.exercise.service.TicketSearchService;
import com.joha.generated.api.SearchApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class SearchController implements SearchApi {

    private final TicketSearchService ticketSearchService;

    public SearchController(TicketSearchService ticketSearchService) {
        this.ticketSearchService = ticketSearchService;
    }

    @Override
    public ResponseEntity<Void> search(String attribute, String value) {
        try {
            ticketSearchService.searchForTicketsByStreaming(attribute, value);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("searchUsingDataMapping")
    public List<Ticket> searchUsingDataMapping(String attribute, String value) throws IOException {
        return ticketSearchService.searchForTicketsByByDataMapping(attribute, value);
    }

    @GetMapping("searchUsingStream")
    public String searchUsingStream(String attribute, String value) throws IOException {
        return ticketSearchService.searchForTicketsByStreaming(attribute, value).toString();
    }

    //@GetMapping("searchUsingStreamAsync")
    //public String searchUsingStreamAsync(String attribute, String value) throws IOException, ExecutionException, InterruptedException {
    //    return ticketSearchService.searchForTicketsByStreamingAsync(attribute, value).toString();
    //}
}
