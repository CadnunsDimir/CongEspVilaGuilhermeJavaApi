package io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment;

import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.Sheet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SheetRepository implements PanacheRepository<Sheet>{

    @Transactional
    public Sheet findLastServiceYear(int serviceYear) {
        return find("where serviceYear=?1", serviceYear).firstResult();
    }
}
