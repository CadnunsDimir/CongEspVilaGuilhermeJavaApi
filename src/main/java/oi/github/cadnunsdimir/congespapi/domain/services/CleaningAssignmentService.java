package oi.github.cadnunsdimir.congespapi.domain.services;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.domain.models.CleaningAssingmentDate;
import oi.github.cadnunsdimir.congespapi.infra.repositories.congregation.GroupRepository;

@ApplicationScoped
@AllArgsConstructor
public class CleaningAssignmentService extends MeetingListServiceBase {
    private GroupRepository groupRepository;

    public List<CleaningAssingmentDate> getList() {
        var currentDate = this.firstDay();
        List<CleaningAssingmentDate> dates = new ArrayList<>();
        var groups = this.groupRepository.orderByGroupNumber();

        for (int meetingIndex = 0; meetingIndex < meetingsAmount; meetingIndex++) {
            var groupIndex = (meetingIndex % groups.size());
            var group = groups.get(groupIndex);
            dates.add(new CleaningAssingmentDate(currentDate, group, "Después de la reunión"));
            currentDate = this.nextMeetingDate(currentDate);
        }
        return dates;
    }

}
