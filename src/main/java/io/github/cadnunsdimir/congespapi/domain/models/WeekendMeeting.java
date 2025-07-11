package io.github.cadnunsdimir.congespapi.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeekendMeeting {
    private LocalDate date;
    private String president;
    private String speaker;
    private String speakerCongregation;
    private String publicTalkTheme;
    private int outlineNumber;// numero do esboço
    private String watchtowerStudyConductor;
    private String watchtowerStudyReader;
}
