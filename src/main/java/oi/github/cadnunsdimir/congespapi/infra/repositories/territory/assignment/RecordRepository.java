package oi.github.cadnunsdimir.congespapi.infra.repositories.territory.assignment;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.cadnunsdimir.congespapi.entities.territory.assignment.AssignmentRecord;

@ApplicationScoped
public class RecordRepository implements PanacheRepository<AssignmentRecord> {
    public List<AssignmentRecord> listBySheetId(UUID id) {
        return list("sheet.id", id);
    }
}
