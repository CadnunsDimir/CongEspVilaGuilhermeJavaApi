package io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment;

import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.AssignmentRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RecordRepository implements PanacheRepository<AssignmentRecord> {
    public List<AssignmentRecord> listBySheetId(UUID id) {
        return list("sheet.id = ?1 order by assignedDate", id);
    }

    public void updateCompletedDate(UUID recordId, LocalDate completedDate) {
        update("completedDate = ?1 where id = ?2", completedDate, recordId);
    }
}
