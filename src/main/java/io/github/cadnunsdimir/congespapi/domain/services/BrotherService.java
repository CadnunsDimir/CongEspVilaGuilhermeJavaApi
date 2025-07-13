package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.AssignmentTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.BrotherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@ApplicationScoped
@AllArgsConstructor
public class BrotherService {
    private AssignmentTypeRepository assignmentTypeRepository;
    private BrotherRepository brotherRepository;

    public void createOrUpdate(Brother brother) {
        var db = this.brotherRepository.findById(brother.getId());
        final var dbBrother = db != null ? db : new Brother();
        dbBrother.setName(brother.getName());
        dbBrother.setAssignments(new ArrayList<>());
        brother.getAssignments().forEach(a-> {
            var dbAssign = assignmentTypeRepository.createIfNotExistsAndRetrieve(a.getType());
            dbBrother.getAssignments().add(dbAssign);
        });
        this.brotherRepository.persist(dbBrother);
    }
}
