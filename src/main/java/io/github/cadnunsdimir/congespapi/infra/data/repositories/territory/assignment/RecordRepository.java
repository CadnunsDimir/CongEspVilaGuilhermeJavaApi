package io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.AssignmentRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class RecordRepository implements PanacheRepository<AssignmentRecord> {
    public List<AssignmentRecord> listBySheetId(UUID id) {
        return list("sheet.id = ?1 order by assignedDate", id);
    }

    public void updateCompletedDate(UUID recordId, LocalDate completedDate) {
        update("completedDate = ?1 where id = ?2", completedDate, recordId);
    }

    public Map<Integer, LocalDate> listLastCompletedDateByCardFromLastYearService(int serviceYear) {
        var query = find(
                "select card.number, max(completedDate) from AssignmentRecord record " +
                        "join record.sheet sheet " +
                        "join record.territoryNumber card " +
                        "where sheet.serviceYear = ?1 " +
                        "group by card.number", serviceYear);

        List<Object[]> results = query.project(Object[].class).list();
        Map<Integer, LocalDate> datesByCard = new HashMap<>();
        for (Object[] lineObjects : results) {
            var cardNumber = (Integer)lineObjects[0];
            var date = (LocalDate)lineObjects[1];
            datesByCard.put(cardNumber, date);
        }

        return datesByCard;
    }
}
