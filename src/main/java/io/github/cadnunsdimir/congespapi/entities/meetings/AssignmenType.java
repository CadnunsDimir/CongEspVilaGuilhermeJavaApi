package io.github.cadnunsdimir.congespapi.entities.meetings;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "meetings_assignment_type")
@Data
public class AssignmenType {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false, length = 10)
    private String type;
}
