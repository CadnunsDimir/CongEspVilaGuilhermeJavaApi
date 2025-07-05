package io.github.cadnunsdimir.congespapi.domain.models;

import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmenType;
import io.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MeetingAssignmentTemplateModel {
    private String label;
    private UUID typeId;
    public static List<MeetingAssignmentTemplate> map(List<MeetingAssignmentTemplateModel> templateModel) {
        return templateModel.stream().map(x-> {
            var entity = new MeetingAssignmentTemplate();
            entity.setLabel(x.label);
            entity.setType(new AssignmenType());
            entity.getType().setId(x.getTypeId());
            return entity;
        }).toList();
    }
}
