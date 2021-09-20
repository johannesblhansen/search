package com.joha.exercise.service.datamapping;

import com.joha.exercise.domain.Priority;
import com.joha.exercise.domain.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionTicketFieldComparisonComponentTest {

    private ReflectionTicketFieldComparisonComponent unitUnderTest;

    @BeforeEach
    public void init(){
        unitUnderTest = new ReflectionTicketFieldComparisonComponent();
    }

    @Test
    void testThatATicketWithCorrectIdWillBeFound() {
        Ticket ticket = new Ticket();
        Long id = 1L;
        ticket.setId(id);
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        unitUnderTest.checkAndCollectTicketThatMatchInput("id", id.toString(), ticket, matchingTickets);
        assertEquals(id, matchingTickets.get(0).getId());
    }

    @Test
    void testThatATicketWithIncorrectIdWillBeNotFound() {
        Ticket ticket = new Ticket();
        Long id = 1L;
        Long incorrectId = 2L;
        ticket.setId(id);
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        unitUnderTest.checkAndCollectTicketThatMatchInput("id", incorrectId.toString(), ticket, matchingTickets);
        assertTrue(matchingTickets.isEmpty());
    }

    @Test
    void testThatATicketWithCorrectPriorityWillBeFound() {
        Ticket ticket = new Ticket();
        ticket.setPriority(Priority.high);
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        unitUnderTest.checkAndCollectTicketThatMatchInput("priority", "high", ticket, matchingTickets);
        assertEquals(Priority.high, matchingTickets.get(0).getPriority());
    }

    @Test
    void testWrongAttributeInput() {
        Ticket ticket = new Ticket();
        Long id = 1L;
        Long incorrectId = 2L;
        ticket.setId(id);
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        String wrongAttributeInput = "wrongAttributeInput";
        unitUnderTest.checkAndCollectTicketThatMatchInput(wrongAttributeInput, incorrectId.toString(), ticket, matchingTickets);
        assertTrue(matchingTickets.isEmpty());
    }

    @Test
    void testPassingNothing() {
        Ticket ticket = new Ticket();
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        unitUnderTest.checkAndCollectTicketThatMatchInput("", "", ticket, matchingTickets);
        assertTrue(matchingTickets.isEmpty());
    }

    @Test
    void testPassingNull() {
        Ticket ticket = new Ticket();
        ArrayList<Ticket> matchingTickets = new ArrayList<>();
        //Attribute is null
        unitUnderTest.checkAndCollectTicketThatMatchInput(null, "1", ticket, matchingTickets);
        assertTrue(matchingTickets.isEmpty());

        unitUnderTest.checkAndCollectTicketThatMatchInput("attribute", null, ticket, matchingTickets);
        assertTrue(matchingTickets.isEmpty());

        unitUnderTest.checkAndCollectTicketThatMatchInput("attribute", "1", null, matchingTickets);
        assertTrue(matchingTickets.isEmpty());
    }
}