package io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.MeetingAssignmentTemplate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemplateRepository implements PanacheRepository<MeetingAssignmentTemplate> {

}
