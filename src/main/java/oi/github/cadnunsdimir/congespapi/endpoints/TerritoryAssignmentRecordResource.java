package oi.github.cadnunsdimir.congespapi.endpoints;

import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import oi.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentPatchRecord;
import oi.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentRecord;
import oi.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentSheetData;
import oi.github.cadnunsdimir.congespapi.domain.services.TerritoryAssignmentService;

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
