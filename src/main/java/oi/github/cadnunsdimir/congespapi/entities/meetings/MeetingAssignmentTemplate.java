package oi.github.cadnunsdimir.congespapi.entities.meetings;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

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
    private AssignmenType type;
}
