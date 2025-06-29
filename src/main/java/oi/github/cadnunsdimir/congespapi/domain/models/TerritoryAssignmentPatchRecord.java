package oi.github.cadnunsdimir.congespapi.domain.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class TerritoryAssignmentPatchRecord {
    private UUID recordId;
    private LocalDate completedDate;
}
