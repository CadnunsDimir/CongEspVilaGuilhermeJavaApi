package io.github.cadnunsdimir.congespapi.infra.data.entities.meetings;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meetings_assignment_brother")
@Data
public class Brother {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @ManyToMany
    private List<AssignmentType> assignments;
}
