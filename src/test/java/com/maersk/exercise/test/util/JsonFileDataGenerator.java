package com.joha.exercise.test.util;

import com.google.gson.Gson;
import com.joha.exercise.domain.Priority;
import com.joha.exercise.domain.Status;
import com.joha.exercise.domain.Ticket;
import com.joha.exercise.domain.Type;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Used for creating datasets for benchmarking.
 */
public class JsonFileDataGenerator {

    @Test
    public void generateData() throws IOException {
        checkAndCreateTargetDir();
        Gson gson = new Gson();
        List<Ticket> tickets = new ArrayList<>();
        int howManyElements = 5000000;
        for (int i = 0; i < howManyElements; i++){
            Ticket ticket = new Ticket();
            ticket.setId(i + 1L);
            ticket.setDescription("Nostrud ad sit velit cupidatat laboris ipsum");
            ticket.setPriority(Priority.low);
            ticket.setSubject("TicketSubject");
            ticket.setType(Type.incident);
            ticket.setStatus(Status.pending);
            tickets.add(ticket);
        }
        String fileName = "generatedData"+ File.separator +"testFile" + howManyElements + ".json";
        FileWriter writer = new FileWriter(fileName);
        gson.toJson(tickets, writer);
        writer.close();
    }

    private void checkAndCreateTargetDir() throws IOException {
        String fileName = "generatedData";
        File directory = new File(fileName);
        if (!directory.exists()){
            directory.mkdir();
        }
    }
}
