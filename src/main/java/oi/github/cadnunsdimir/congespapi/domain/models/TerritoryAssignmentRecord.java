package oi.github.cadnunsdimir.congespapi.domain.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TerritoryAssignmentRecord {
    private int territoryNumber;
    private String assignedTo;
    private LocalDate assignedDate;
    private LocalDate completedDate; 
}
