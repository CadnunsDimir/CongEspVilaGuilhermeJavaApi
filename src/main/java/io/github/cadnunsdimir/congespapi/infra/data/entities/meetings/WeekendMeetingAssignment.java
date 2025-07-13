package io.github.cadnunsdimir.congespapi.infra.data.entities.meetings;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "meeting_weekend_assignment")
public class WeekendMeetingAssignment {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(unique = true)
    private LocalDate date;
    private String president;
    private String watchtowerStudyConductor;
    private String watchtowerStudyReader;
}
