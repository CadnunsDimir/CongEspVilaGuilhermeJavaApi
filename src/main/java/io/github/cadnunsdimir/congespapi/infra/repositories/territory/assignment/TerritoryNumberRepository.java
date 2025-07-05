package io.github.cadnunsdimir.congespapi.infra.repositories.territory.assignment;

import io.github.cadnunsdimir.congespapi.entities.territory.assignment.TerritoryNumber;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TerritoryNumberRepository implements PanacheRepository<TerritoryNumber> {
    public TerritoryNumber findByNumber(int territoryNumber) {
        return find("number", territoryNumber).firstResult();
    }

}
