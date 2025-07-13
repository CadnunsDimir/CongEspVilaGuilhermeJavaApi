package io.github.cadnunsdimir.congespapi.infra.data.entities.meetings;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "meetings_assignment_template")
@Data
public class MeetingAssignmentTemplate {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 10)
    private String label;

    @ManyToOne
    private AssignmentType type;
}
