package oi.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import java.util.UUID;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.cadnunsdimir.congespapi.entities.meetings.AssignmenType;

@ApplicationScoped
public class AssignmenTypeRepository implements PanacheRepositoryBase<AssignmenType, UUID> {

    public AssignmenType findByType(String type) {
        return find("type", type).firstResult();
    }
}
