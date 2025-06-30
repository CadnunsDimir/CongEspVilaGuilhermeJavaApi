package oi.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.cadnunsdimir.congespapi.entities.meetings.Brother;

@ApplicationScoped
public class BrotherRepository implements PanacheRepository<Brother> {
    public Map<String, List<Brother>> groupedByAssignment() {
        var query = this.find(
            "select brother, assignment.type from Brother brother" +
            " join brother.assignments assignment");

        List<Object[]> results = query.project(Object[].class).list();
        Map<String, List<Brother>> groupedResults = new HashMap<>();
        for (Object[] lineObjects : results) {
            Brother brother = (Brother)lineObjects[0];
            String type = (String)lineObjects[1];
            groupedResults
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(brother);
        }
        return groupedResults;
    }
}
