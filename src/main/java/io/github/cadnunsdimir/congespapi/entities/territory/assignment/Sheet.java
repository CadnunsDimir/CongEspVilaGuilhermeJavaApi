package io.github.cadnunsdimir.congespapi.entities.territory.assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "territory_assignment_sheets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sheet {
    @Id
    private UUID id = UUID.randomUUID();   
    private int serviceYear;
}
