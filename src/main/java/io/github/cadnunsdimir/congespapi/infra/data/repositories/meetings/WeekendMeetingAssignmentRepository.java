package io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.WeekendMeetingAssignment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeekendMeetingAssignmentRepository implements PanacheRepository<WeekendMeetingAssignment> {

    public List<WeekendMeetingAssignment> listBetweenDates(LocalDate firstDate, LocalDate endDate) {
        return list("from WeekendMeetingAssignment where date between ?1 and ?2", firstDate, endDate);
    }

    public Optional<WeekendMeetingAssignment> findByDate(LocalDate date) {
        return find("date", date).firstResultOptional();
    }

}
