package oi.github.cadnunsdimir.congespapi.endpoints;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.domain.models.MeetingAssignmentTemplateModel;
import oi.github.cadnunsdimir.congespapi.domain.services.MeetingAssignmentService;
import oi.github.cadnunsdimir.congespapi.domain.services.MeetingAssignmentTemplateService;
import oi.github.cadnunsdimir.congespapi.entities.meetings.AssignmenType;
import oi.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import oi.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.*;
import oi.github.cadnunsdimir.congespapi.infra.services.CsvReaderService;

@Path("/api/meetings/assignment/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class MeetingsAssignmentResource {
    private AssignmenTypeRepository assignmenTypeRepository;
    private BrotherRepository brotherRepository;
    private CsvReaderService csvReader;
    private MeetingAssignmentTemplateService templateService;
    private MeetingAssignmentService meetingAssignmentService; 

    @GET
    public Response getSchedule(){
        return Response.ok(
            this.meetingAssignmentService.getSchedule())
            .build();
    }

    @GET
    @Path("/type")
    public List<AssignmenType> getTypes() {
        return this.assignmenTypeRepository.listAll();
    }

    @POST
    @Path("/type")
    @Transactional
    public Response createType(AssignmenType newRecord) {
        this.assignmenTypeRepository.persist(newRecord);
        return Response.status(StatusCode.CREATED).build();
    }

    @GET
    @Path("/brother")
    public List<Brother> getBrothers() {
        return this.brotherRepository.listAll();
    }

    @POST
    @Path("/brother")
    @Transactional
    public Response createBrother(Brother brother) {
        this.brotherRepository.persist(brother);
        return Response.status(StatusCode.CREATED).build();
    }

    @POST
    @Path("/brother/csv")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createBrotherCsv(String csv) {
        List<Brother> brothers = this.csvReader.toBrotherList(csv);
        this.brotherRepository.persist(brothers);
        return Response.status(StatusCode.CREATED).build();
    }

    @PUT
    @Path("/template")
    @Transactional
    public Response addTemplate(List<MeetingAssignmentTemplateModel> templateModel) {
        List<MeetingAssignmentTemplate> template = MeetingAssignmentTemplateModel.map(templateModel);
        this.templateService.updateTemplate(template);
        return Response.status(StatusCode.OK).build();
    }
}
