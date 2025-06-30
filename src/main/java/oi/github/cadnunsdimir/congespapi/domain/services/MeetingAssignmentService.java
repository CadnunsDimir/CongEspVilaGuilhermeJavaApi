package oi.github.cadnunsdimir.congespapi.domain.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.domain.models.BrotherAssigner;
import oi.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.BrotherRepository;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.TemplateRepository;

@ApplicationScoped
@AllArgsConstructor
public class MeetingAssignmentService {
    private TemplateRepository templateRepository;
    private BrotherRepository brotherRepository;

    public List<List<Object>> getSchedule() {
        var meetings = new ArrayList<List<Object>>();
        var currentDate = this.firstDay();        
        int meetingsAmount = 19;
        var template = getTemplate();
        var headerArray = new ArrayList<Object>();
        headerArray.add("Date");
        template.forEach(x-> headerArray.add(x.getLabel()));
        List<BrotherAssigner> assigners = getAssigner();
        meetings.add(headerArray);

        for (int meetingIndex = 0; meetingIndex < meetingsAmount; meetingIndex++) {
            var date = new ArrayList<Object>();
            date.add(currentDate);
            for (MeetingAssignmentTemplate templateItem : template) {
                var assigner = assigners.stream()
                    .filter(x-> 
                        x.getType().equals(templateItem.getType().getType()))
                    .findFirst()
                    .orElseThrow();
                date.add(assigner.next(date).getName());                
            }
            meetings.add(date);
            currentDate = this.nextMeetingDate(currentDate);
        }

        return meetings;
    }

    private List<BrotherAssigner> getAssigner() {
        List<BrotherAssigner> list = new ArrayList<>();
        this.brotherRepository.groupedByAssignment().forEach((key, value)-> 
            list.add(new BrotherAssigner(value, key)));
        return list;
    }

    private List<MeetingAssignmentTemplate> getTemplate() {
       return templateRepository.listAll();
    }

    private LocalDate firstDay() {
        var nextMonth = LocalDate.now().plusMonths(1);
        return nextMeetingDate(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(),1));
    }

    private LocalDate nextMeetingDate(LocalDate currentDate) {
        List<DayOfWeek> weekDays = Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
         var meetingDay = currentDate;
        do{
            meetingDay = meetingDay.plusDays(1);
        }while(!weekDays.contains(meetingDay.getDayOfWeek()));
        return meetingDay;
    }
}
