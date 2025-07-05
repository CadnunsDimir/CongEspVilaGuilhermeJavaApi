package io.github.cadnunsdimir.congespapi.endpoints;

import io.github.cadnunsdimir.congespapi.entities.congregation.CongregationGroup;
import io.github.cadnunsdimir.congespapi.infra.repositories.congregation.GroupRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.List;

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
