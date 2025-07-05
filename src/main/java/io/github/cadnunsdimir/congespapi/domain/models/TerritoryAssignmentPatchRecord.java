package io.github.cadnunsdimir.congespapi.domain.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TerritoryAssignmentPatchRecord {
    private UUID recordId;
    private LocalDate completedDate;
}
