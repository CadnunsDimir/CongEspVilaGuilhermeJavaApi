package oi.github.cadnunsdimir.congespapi.infra.repositories.congregation;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import oi.github.cadnunsdimir.congespapi.entities.congregation.CongregationGroup;

@ApplicationScoped
public class GroupRepository implements PanacheRepository<CongregationGroup>{
    public List<CongregationGroup> orderByGroupNumber() {
        return listAll(Sort.by("groupNumber"));
    }
}
