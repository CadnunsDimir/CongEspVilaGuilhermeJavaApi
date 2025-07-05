package io.github.cadnunsdimir.congespapi.endpoints;

import io.github.cadnunsdimir.congespapi.domain.services.CleaningAssignmentService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/api/meetings/cleaning")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class CleaningListResource {
    private CleaningAssignmentService cleaningService;

    @GET
    public Response getList() {
        return Response.ok(this.cleaningService.getList()).build();
    }
}
