package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.enums.AssignmentTypeEnum;
import io.github.cadnunsdimir.congespapi.domain.models.BrotherAssigner;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendMeeting;
import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.entities.meetings.PublicTalk;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmentTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.BrotherRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.PublicTalkRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.github.cadnunsdimir.congespapi.domain.enums.AssignmentTypeEnum.*;

@ApplicationScoped
@AllArgsConstructor
public class WeekendMeetingService extends MeetingListServiceBase{
    private BrotherRepository brotherRepository;
    private AssignmentTypeRepository assignmentTypeRepository;
    private PublicTalkRepository publicTalkRepository;
    public List<WeekendMeeting> getCurrentSchedule() {
        DayOfWeek[] sunday = {DayOfWeek.SUNDAY};
        var list = new ArrayList<WeekendMeeting>();

        var startDate = firstDay(sunday);
        var endDate = startDate.plusMonths(2);
        var currentDate = startDate;
        var publicTalks = publicTalkRepository.listBetweenDates(startDate, endDate);
        var presidentType = WEEKEND_MEETING_PRESIDENT.getType();
        var readerType = WATCHTOWER_STUDY_READER.getType();
        var presidentAssigner = new BrotherAssigner(brotherRepository.findByAssignment(presidentType), presidentType);
        var readerAssigner = new BrotherAssigner(brotherRepository.findByAssignment(readerType), readerType);

        readerAssigner.revertList();

        while (currentDate.isBefore(endDate)){
            LocalDate finalCurrentDate = currentDate;
            var ignoreList = new ArrayList<>();

            var conductor = brotherRepository.findByAssignment(WATCHTOWER_STUDY_CONDUCTOR.getType())
                    .getFirst()
                    .getName();
            ignoreList.add(conductor);
            var publicTalk = publicTalks.stream()
                    .filter(x-> x.getDate().equals(finalCurrentDate))
                    .findFirst()
                    .orElse(new PublicTalk());
            if(publicTalk.isLocal()){
                ignoreList.add(publicTalk.getSpeaker());
            }
            var president = presidentAssigner.next(ignoreList).getName();
            ignoreList.add(president);
            var reader = readerAssigner.next(ignoreList).getName();

            list.add(new WeekendMeeting(
                    finalCurrentDate,
                    president,
                    publicTalk.getSpeaker(),
                    publicTalk.isLocal() ? "Arreglo Local" : publicTalk.getCongregation(),
                    publicTalk.getPublicTalkTheme(),
                    publicTalk.getOutlineNumber(),
                    conductor,
                    reader));
            currentDate = finalCurrentDate.plusWeeks(1);
        }
        return list;
    }

    @Transactional
    public void setWatchtowerStudyConductor(Brother brother) {
        AssignmentType watchtowerStudyConductorType = assignmentTypeRepository.findByType(
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
        var brothers = this.brotherRepository.findByAssignment(assignmentType.getType());
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
