package io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "territory_assignment_records")
public class AssignmentRecord {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String assignedTo;
    private LocalDate assignedDate;
    private LocalDate completedDate;

    @ManyToOne
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    @ManyToOne
    @JoinColumn(name = "territory_number_id")
    private TerritoryNumber territoryNumber;
}
