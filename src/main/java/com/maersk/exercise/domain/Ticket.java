package com.joha.exercise.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    Long id;
    Type type;
    String subject;
    String description;
    Priority priority;
    Status status;
}
