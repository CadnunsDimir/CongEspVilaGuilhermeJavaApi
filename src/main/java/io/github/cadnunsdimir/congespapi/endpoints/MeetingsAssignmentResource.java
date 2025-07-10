package io.github.cadnunsdimir.congespapi.endpoints;

import io.github.cadnunsdimir.congespapi.domain.models.MeetingAssignmentTemplateModel;
import io.github.cadnunsdimir.congespapi.domain.services.MeetingAssignmentService;
import io.github.cadnunsdimir.congespapi.domain.services.MeetingAssignmentTemplateService;
import io.github.cadnunsdimir.congespapi.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.entities.meetings.MeetingAssignmentTemplate;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmentTypeRepository;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.BrotherRepository;
import io.github.cadnunsdimir.congespapi.infra.services.CsvReaderService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import java.util.List;

@Path("/api/meetings/assignment/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class MeetingsAssignmentResource {
    private AssignmentTypeRepository assignmentTypeRepository;
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
    public List<AssignmentType> getTypes() {
        return this.assignmentTypeRepository.listAll();
    }

    @POST
    @Path("/type")
    @Transactional
    public Response createType(AssignmentType newRecord) {
        this.assignmentTypeRepository.persist(newRecord);
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
