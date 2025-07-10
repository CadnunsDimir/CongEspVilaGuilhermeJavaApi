package io.github.cadnunsdimir.congespapi.entities.meetings;

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
    private int outlineNumber;
    private boolean isLocal;
    private String congregation;
}
