package io.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmenType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AssignmenTypeRepository implements PanacheRepositoryBase<AssignmenType, UUID> {

    public AssignmenType findByType(String type) {
        return find("type", type).firstResult();
    }
}
