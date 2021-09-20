package com.joha.exercise.service.datamapping;

import com.joha.exercise.domain.Priority;
import com.joha.exercise.domain.Status;
import com.joha.exercise.domain.Ticket;
import com.joha.exercise.domain.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Component
public class ReflectionTicketFieldComparisonComponent {

    public void checkAndCollectTicketThatMatchInput(String attribute, String value, Ticket ticket, List<Ticket> matchingTickets){
        try {
            if (attribute == null || ticket == null){
                return;
            }
            Field field = ticket.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            if (field.getType() == Long.class) {
                Long fieldValue = (Long) field.get(ticket);
                if (value.equals(fieldValue.toString())) {
                    matchingTickets.add(ticket);
                }
            }
            if (field.getType() == String.class) {
                String fieldValue = (String) field.get(ticket);
                if (value.equals(fieldValue)) {
                    matchingTickets.add(ticket);
                }
            }
            if (field.getType() == Status.class){
                Status fieldValue = (Status) field.get(ticket);
                if (value.equals(fieldValue.toString())) {
                    matchingTickets.add(ticket);
                }
            }
            if (field.getType() == Priority.class){
                Priority fieldValue = (Priority) field.get(ticket);
                if (value.equals(fieldValue.toString())) {
                    matchingTickets.add(ticket);
                }
            }
            if (field.getType() == Type.class){
                Type fieldValue = (Type) field.get(ticket);
                if (value.equals(fieldValue.toString())) {
                    matchingTickets.add(ticket);
                }
            }
        } catch (NoSuchFieldException e) {
            log.error("The input attribute: " + attribute + "- was not found as a field on the ticket class. Input is invalid");
        } catch (IllegalAccessException e) {
            //This cannot happen
            e.printStackTrace();
        }
    }
}
