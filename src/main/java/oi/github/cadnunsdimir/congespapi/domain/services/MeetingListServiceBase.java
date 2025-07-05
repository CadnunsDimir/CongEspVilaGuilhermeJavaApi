package oi.github.cadnunsdimir.congespapi.domain.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingListServiceBase {
           
    protected int meetingsAmount = 19;
    protected LocalDate firstDay() {
        var currentDate = LocalDate.now();
        var firstDay = LocalDate.of(currentDate.getYear(), currentDate.getMonth(),1);
        if (currentDate.getDayOfMonth() > 20) {
            firstDay = firstDay.plusMonths(1);
        }
        return nextMeetingDate(firstDay);
    }

    protected LocalDate nextMeetingDate(LocalDate currentDate) {
        List<DayOfWeek> weekDays = Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
         var meetingDay = currentDate;
        do{
            meetingDay = meetingDay.plusDays(1);
        }while(!weekDays.contains(meetingDay.getDayOfWeek()));
        return meetingDay;
    }
}
