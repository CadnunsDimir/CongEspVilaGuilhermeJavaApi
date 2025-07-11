package io.github.cadnunsdimir.congespapi.endpoints;

import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.entities.meetings.PublicTalk;
import io.github.cadnunsdimir.congespapi.domain.models.WeekendMeeting;
import io.github.cadnunsdimir.congespapi.domain.services.WeekendMeetingService;
import io.github.cadnunsdimir.congespapi.infra.repositories.meetings.PublicTalkRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/meetings/weekends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class WeekendMeetingsResource {
    private WeekendMeetingService weekendMeetingService;
    private PublicTalkRepository publicTalkRepository;

    @GET
    public List<WeekendMeeting> get(){
        return weekendMeetingService.getCurrentSchedule();
    }

    @POST
    @Path("/public-talk")
    public Response schedulePublicTalk(PublicTalk publicTalk){
        publicTalkRepository.updateOrCreateNew(publicTalk);
        return Response.status(RestResponse.StatusCode.OK).build();
    }

    @GET
    @Path("/public-talk")
    public Response listPublicTalk(
            @QueryParam("month") int month,
            @QueryParam("year") int year){
        return Response.ok(publicTalkRepository.listByMonth(month, year)).build();
    }

    @POST
    @Path("/watchtower-study-conductor")
    public Response setWatchtowerStudyConductor(Brother brother){
        this.weekendMeetingService.setWatchtowerStudyConductor(brother);
        return Response.status(RestResponse.StatusCode.OK).build();
    }
}
