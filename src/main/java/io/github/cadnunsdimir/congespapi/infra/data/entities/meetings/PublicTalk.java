package io.github.cadnunsdimir.congespapi.infra.data.entities.meetings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table (name = "meeting_weekend_publictalk")
public class PublicTalk {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private LocalDate date;
    private String speaker;
    private String publicTalkTheme;
    private Integer outlineNumber = 0;
    private Boolean isLocal = false;
    private String congregation;
}
