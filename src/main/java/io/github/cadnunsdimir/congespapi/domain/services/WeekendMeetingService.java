package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.enums.AssignmentTypeEnum;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendMeeting;
import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmentTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.BrotherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

import static io.github.cadnunsdimir.congespapi.domain.enums.AssignmentTypeEnum.WATCHTOWER_STUDY_CONDUCTOR;

@ApplicationScoped
@AllArgsConstructor
public class WeekendMeetingService {
    private BrotherRepository brotherRepository;
    private AssignmentTypeRepository assignmentTypeRepository;
    public List<WeekendMeeting> getCurrentSchedule() {
        return List.of(new WeekendMeeting());
    }

    @Transactional
    public void setWatchtowerStudyConductor(Brother brother) {
        AssignmentType watchtowerStudyConductorType = assignmentTypeRepository.createIfNotExistsAndRetrieve(
                WATCHTOWER_STUDY_CONDUCTOR.getType());
        removeFromPrevious(watchtowerStudyConductorType);
        addTypeToBrother(brother, watchtowerStudyConductorType);
    }

    private void addTypeToBrother(Brother brother, AssignmentType assignmentType) {
        var brotherFromDb = this.brotherRepository.findById(brother.getId());
        brotherFromDb.getAssignments().add(assignmentType);
        this.brotherRepository.persist(brotherFromDb);
    }

    private void removeFromPrevious(AssignmentType assignmentType) {
        var brothers = this.brotherRepository.findByAssignment(assignmentType);
        for (Brother brother: brothers) {
            var typeToRemove = brother.getAssignments()
                    .stream().filter(x->x.getType() == assignmentType.getType())
                    .findFirst()
                    .get();
            brother.getAssignments().remove(typeToRemove);
            this.brotherRepository.persist(brother);
        }
    }
}
