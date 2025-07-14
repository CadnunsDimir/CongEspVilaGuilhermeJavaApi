package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.enums.AssignmentTypeEnum;
import io.github.cadnunsdimir.congespapi.domain.models.BrotherAssigner;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendAllowedBrothers;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendAssingmentRequest;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendMeeting;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.PublicTalk;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.WeekendMeetingAssignment;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.AssignmentTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.BrotherRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.PublicTalkRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.WeekendMeetingAssignmentRepository;
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
    private WeekendMeetingAssignmentRepository weekendMeetingAssignmentRepository;

    @Transactional
    public List<WeekendMeeting> getCurrentSchedule() {
        DayOfWeek[] sunday = {DayOfWeek.SUNDAY};
        var list = new ArrayList<WeekendMeeting>();

        var startDate = firstDay(sunday);
        var endDate = startDate.plusMonths(2);
        var currentDate = startDate;
        var publicTalks = publicTalkRepository.listBetweenDates(startDate, endDate);
        var persistedList = this.weekendMeetingAssignmentRepository.listBetweenDates(startDate, endDate);
        
        if (!persistedList.isEmpty()) {
            return this.join(persistedList, publicTalks);
        }

        var presidentAssigner = getAlternatedAssigner(WEEKEND_MEETING_PRESIDENT);
        var readerAssigner = getAlternatedAssigner(WATCHTOWER_STUDY_READER);

        while (currentDate.isBefore(endDate)){
            LocalDate finalCurrentDate = currentDate;
            var ignoreList = new ArrayList<>();

            var conductor = brotherRepository.findByAssignment(WATCHTOWER_STUDY_CONDUCTOR.getType())
                    .getFirst()
                    .getName();
            ignoreList.add(conductor);
            var publicTalk = getTalk(publicTalks, finalCurrentDate);
            if(Boolean.TRUE.equals(publicTalk.getIsLocal())){
                ignoreList.add(publicTalk.getSpeaker());
            }
            var president = presidentAssigner.next(ignoreList).getName();
            ignoreList.add(president);
            var reader = readerAssigner.next(ignoreList).getName();

            list.add(new WeekendMeeting(
                    finalCurrentDate,
                    president,
                    publicTalk.getSpeaker(),
                    Boolean.TRUE.equals(publicTalk.getIsLocal()) ? "Arreglo Local" : publicTalk.getCongregation(),
                    publicTalk.getPublicTalkTheme(),
                    publicTalk.getOutlineNumber(),
                    conductor,
                    reader));
            currentDate = finalCurrentDate.plusWeeks(1);
        }
        this.persist(list);
        return list;
    }

    private PublicTalk getTalk(List<PublicTalk> publicTalks, LocalDate finalCurrentDate) {
        return publicTalks.stream()
                    .filter(x-> x.getDate().equals(finalCurrentDate))
                    .findFirst()
                    .orElse(new PublicTalk());
    }

    public String mapCongregation(PublicTalk publicTalk){
        return Boolean.TRUE.equals(publicTalk.getIsLocal()) ? "Arreglo Local" : publicTalk.getCongregation();
    }

    private List<WeekendMeeting> join(List<WeekendMeetingAssignment> meetings, List<PublicTalk> publicTalks) {
        return meetings.stream().map(m-> {
            var publicTalk = getTalk(publicTalks, m.getDate());
            return new WeekendMeeting(
                m.getDate(),
                m.getPresident(),
                publicTalk.getSpeaker(),
                mapCongregation(publicTalk),
                publicTalk.getPublicTalkTheme(),
                publicTalk.getOutlineNumber(),
                m.getWatchtowerStudyConductor(),
                m.getWatchtowerStudyReader()
            );
        }).toList();
    }

    private void persist(ArrayList<WeekendMeeting> list) {
        var dbList = list.stream().map(this::mapToDb).toList();
        this.weekendMeetingAssignmentRepository.persist(dbList);
    }

    private WeekendMeetingAssignment mapToDb(WeekendMeeting wm){
        return new WeekendMeetingAssignment(
            null,
            wm.getDate(), 
            wm.getPresident(), 
            wm.getWatchtowerStudyConductor(), 
            wm.getWatchtowerStudyReader());
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
        var type = assignmentType.getType();
        var brothers = this.brotherRepository.findByAssignment(type);
        for (Brother brother: brothers) {
            var typeToRemove = brother.getAssignments()
                    .stream().filter(x-> type.equals(x.getType()))
                    .findFirst()
                    .orElseThrow();
            brother.getAssignments().remove(typeToRemove);
            this.brotherRepository.persist(brother);
        }
    }

    @Transactional
    public void updateMeeting(WeekendAssingmentRequest changes) {
        var meeting = this.weekendMeetingAssignmentRepository.findByDate(changes.getDate())
            .orElseThrow();
        meeting.setPresident(changes.getPresident());
        meeting.setWatchtowerStudyConductor(changes.getWatchtowerStudyConductor());
        meeting.setWatchtowerStudyReader(changes.getWatchtowerStudyReader());
        this.weekendMeetingAssignmentRepository.persist(meeting);
    }

    public BrotherAssigner getAlternatedAssigner(AssignmentTypeEnum type) {
        var assigner = new BrotherAssigner(brotherRepository.findByAssignment(type.getType()), type.getType(), true);
        assigner.alternateOrder();
        return assigner;
    }

    public WeekendAllowedBrothers getBrothers() {
        var presidentAssigner = getAlternatedAssigner(WEEKEND_MEETING_PRESIDENT);
        var readerAssigner = getAlternatedAssigner(WATCHTOWER_STUDY_READER);
        return new WeekendAllowedBrothers(
            presidentAssigner.getBrothers(),
            readerAssigner.getBrothers()
        );
    }
}
