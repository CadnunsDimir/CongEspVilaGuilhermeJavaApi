package io.github.cadnunsdimir.congespapi.domain.models;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class WeekendAssingmentRequest {
    private LocalDate date;
    private String president;
    private String watchtowerStudyConductor;
    private String watchtowerStudyReader;
}
