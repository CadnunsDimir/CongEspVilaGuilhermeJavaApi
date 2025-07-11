package io.github.cadnunsdimir.congespapi.domain.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingListServiceBase {
    protected int meetingsAmount = 19;
    DayOfWeek[] fullWeekdays = {DayOfWeek.SUNDAY, DayOfWeek.THURSDAY};
    protected LocalDate firstDay(DayOfWeek[] days) {
        var currentDate = LocalDate.now();
        var firstDay = LocalDate.of(currentDate.getYear(), currentDate.getMonth(),1);
        var isEvenMonth = currentDate.getMonth().getValue() % 2 == 0;
        var isEndOfMonth = currentDate.getDayOfMonth() > 20;
        if (isEvenMonth && isEndOfMonth) {
            firstDay = firstDay.plusMonths(1);
        }
        return nextMeetingDate(firstDay, days);
    }

    protected LocalDate nextMeetingDate(LocalDate currentDate, DayOfWeek[] weekDays) {
        List<DayOfWeek> weekDaysList = Arrays.asList(weekDays);
         var meetingDay = currentDate;
        do{
            meetingDay = meetingDay.plusDays(1);
        }while(!weekDaysList.contains(meetingDay.getDayOfWeek()));
        return meetingDay;
    }
}
