package oi.github.cadnunsdimir.congespapi.entities.territory.assignment;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
