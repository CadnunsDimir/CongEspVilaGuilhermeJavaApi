package io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

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
