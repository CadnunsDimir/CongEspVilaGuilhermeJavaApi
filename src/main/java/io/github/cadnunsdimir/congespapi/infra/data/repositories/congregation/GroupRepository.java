package io.github.cadnunsdimir.congespapi.infra.data.repositories.congregation;

import io.github.cadnunsdimir.congespapi.infra.data.entities.congregation.CongregationGroup;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GroupRepository implements PanacheRepository<CongregationGroup>{
    public List<CongregationGroup> orderByGroupNumber() {
        return listAll(Sort.by("groupNumber"));
    }
}
