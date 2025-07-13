package io.github.cadnunsdimir.congespapi.domain.models;

import lombok.Data;

import java.time.LocalDate;

import io.github.cadnunsdimir.congespapi.infra.data.entities.congregation.CongregationGroup;

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
