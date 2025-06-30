package oi.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;

@ApplicationScoped
public class TemplateRepository implements PanacheRepository<MeetingAssignmentTemplate> {

}
