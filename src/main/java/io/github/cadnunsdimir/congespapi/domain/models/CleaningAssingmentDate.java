package io.github.cadnunsdimir.congespapi.domain.models;

import io.github.cadnunsdimir.congespapi.entities.congregation.CongregationGroup;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CleaningAssingmentDate {
    public CleaningAssingmentDate(LocalDate date, CongregationGroup group, String assingmentInfo) {
        this.date = date;
        this.groupNumber = group.getGroupNumber();
        this.groupOverseer = group.getGroupOverseer();
        this.assingmentInfo = assingmentInfo;
    }
    private LocalDate date;
    private int groupNumber;
    private String groupOverseer;
    private String assingmentInfo;
}
