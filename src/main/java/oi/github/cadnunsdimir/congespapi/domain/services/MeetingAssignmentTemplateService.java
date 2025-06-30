package oi.github.cadnunsdimir.congespapi.domain.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmenTypeRepository;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.TemplateRepository;

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
