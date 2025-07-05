package oi.github.cadnunsdimir.congespapi.endpoints;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.entities.congregation.CongregationGroup;
import oi.github.cadnunsdimir.congespapi.infra.repositories.congregation.GroupRepository;

@Path("/api/congregation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class CongregationResource {
    private GroupRepository groupRepository;

    @GET
    @Path("/groups")
    public Response listGroups() {
        return Response.ok(groupRepository.orderByGroupNumber()).build();
    }

    @PATCH
    @Path("/groups")
    @Transactional
    public Response updateGroups(List<CongregationGroup> groups) {
        groupRepository.deleteAll();
        groupRepository.persist(groups);
        return Response.ok().build();
    }
}
