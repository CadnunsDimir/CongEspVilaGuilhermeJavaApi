package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.models.CleaningAssingmentDate;
import io.github.cadnunsdimir.congespapi.infra.repositories.congregation.GroupRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class CleaningAssignmentService extends MeetingListServiceBase {
    private GroupRepository groupRepository;

    public List<CleaningAssingmentDate> getList() {
        var currentDate = this.firstDay(fullWeekdays);
        List<CleaningAssingmentDate> dates = new ArrayList<>();
        var groups = this.groupRepository.orderByGroupNumber();

        for (int meetingIndex = 0; meetingIndex < meetingsAmount; meetingIndex++) {
            var groupIndex = (meetingIndex % groups.size());
            var group = groups.get(groupIndex);
            dates.add(new CleaningAssingmentDate(currentDate, group, "Después de la reunión"));
            currentDate = this.nextMeetingDate(currentDate, fullWeekdays);
        }
        return dates;
    }

}
