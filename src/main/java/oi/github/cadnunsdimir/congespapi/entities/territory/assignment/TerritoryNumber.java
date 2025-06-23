package oi.github.cadnunsdimir.congespapi.entities.territory.assignment;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "territory_assignment_territory_numbers")
public class TerritoryNumber {
    @Id
    private UUID id = UUID.randomUUID();
    private int number;
    private LocalDate lastDateCompleted;
}
