package io.github.cadnunsdimir.congespapi.endpoints;

import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentPatchRecord;
import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentRecord;
import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentSheetData;
import io.github.cadnunsdimir.congespapi.domain.services.TerritoryAssignmentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

@Path("/api/territory/assignment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TerritoryAssignmentRecordResource {
    private TerritoryAssignmentService service;

    public TerritoryAssignmentRecordResource(TerritoryAssignmentService service) {
        this.service = service;
    }

    @GET
    public TerritoryAssignmentSheetData getCurrentSheet() {
        return service.getCurrentSheetData();
    }

    @POST
    @Path("/record")
    public Response createRecord(TerritoryAssignmentRecord newRecord) {
        service.addRecord(newRecord);
        return Response.status(StatusCode.CREATED).build();
    }

    @PATCH
    @Path("/record")
    public Response patchRecord(TerritoryAssignmentPatchRecord patchedRecord) {
        service.patchRecord(patchedRecord);
        return Response.status(StatusCode.OK).build();
    }
}
