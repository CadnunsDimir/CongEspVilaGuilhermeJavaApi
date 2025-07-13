package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.models.BrotherAssigner;
import io.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.BrotherRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.TemplateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class MeetingAssignmentService extends MeetingListServiceBase {
    private TemplateRepository templateRepository;
    private BrotherRepository brotherRepository;

    public List<List<Object>> getSchedule() {
        var meetings = new ArrayList<List<Object>>();
        var currentDate = this.firstDay(fullWeekdays);
        var template = getTemplate();
        var headerArray = new ArrayList<>();
        headerArray.add("Date");
        template.forEach(x-> headerArray.add(x.getLabel()));
        List<BrotherAssigner> assigners = getAssigner();
        meetings.add(headerArray);

        for (int meetingIndex = 0; meetingIndex < meetingsAmount; meetingIndex++) {
            var date = new ArrayList<>();
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
            currentDate = this.nextMeetingDate(currentDate, fullWeekdays);
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

    }
