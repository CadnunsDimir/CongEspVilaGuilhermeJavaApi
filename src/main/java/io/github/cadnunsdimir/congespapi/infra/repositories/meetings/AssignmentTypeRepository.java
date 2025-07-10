package io.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmentType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AssignmentTypeRepository implements PanacheRepositoryBase<AssignmentType, UUID> {

    public AssignmentType findByType(String type) {
        return find("type", type).firstResult();
    }

    public AssignmentType createIfNotExistsAndRetrieve(String type) {
        var typeOnDb = findByType(type);
        if(typeOnDb == null) {
            typeOnDb = new AssignmentType();
            typeOnDb.setType(type);
            typeOnDb.setId(UUID.randomUUID());
            persist(typeOnDb);
        }
        return typeOnDb;
    }
}
