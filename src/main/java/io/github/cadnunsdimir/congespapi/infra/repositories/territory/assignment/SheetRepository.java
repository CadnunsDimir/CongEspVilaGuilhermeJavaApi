package io.github.cadnunsdimir.congespapi.infra.repositories.territory.assignment;

import io.github.cadnunsdimir.congespapi.entities.territory.assignment.Sheet;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SheetRepository implements PanacheRepository<Sheet>{

    @Transactional
    public Sheet findLastServiceYear() {
        return find("ORDER BY serviceYear DESC").firstResult();
    }
}
