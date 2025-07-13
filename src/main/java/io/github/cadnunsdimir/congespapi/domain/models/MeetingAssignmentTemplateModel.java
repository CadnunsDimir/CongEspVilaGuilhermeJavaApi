package io.github.cadnunsdimir.congespapi.domain.models;

import lombok.Data;

import java.util.List;
import java.util.UUID;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.MeetingAssignmentTemplate;

@Data
public class MeetingAssignmentTemplateModel {
    private String label;
    private UUID typeId;
    public static List<MeetingAssignmentTemplate> map(List<MeetingAssignmentTemplateModel> templateModel) {
        return templateModel.stream().map(x-> {
            var entity = new MeetingAssignmentTemplate();
            entity.setLabel(x.label);
            entity.setType(new AssignmentType());
            entity.getType().setId(x.getTypeId());
            return entity;
        }).toList();
    }
}
