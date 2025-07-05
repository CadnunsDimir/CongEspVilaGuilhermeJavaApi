package io.github.cadnunsdimir.congespapi.domain.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TerritoryAssignmentRecord {
    private int territoryNumber;
    private String assignedTo;
    private LocalDate assignedDate;
    private LocalDate completedDate; 
}
