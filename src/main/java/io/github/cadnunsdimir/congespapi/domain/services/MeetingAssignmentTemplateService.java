package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmenTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.TemplateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class MeetingAssignmentTemplateService {
    private TemplateRepository repository;
    private AssignmenTypeRepository assignmenTypeRepository;
    
    public void updateTemplate(List<MeetingAssignmentTemplate> template) {
        for (MeetingAssignmentTemplate templateItem : template) {
            templateItem.setType(assignmenTypeRepository.findById(
                templateItem.getType().getId()));
        }
        this.repository.deleteAll();
        this.repository.persist(template);
    }

}
