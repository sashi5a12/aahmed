package com.sample.ch04;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/gradeservice/")
@Produces("application/xml")
public interface GradeManager {

    @GET
    @Path("/grades")
    @Produces("application/xml")
    public String getGrades();

    @GET
    @Path("/grade/{grade}")
    public String getGradeSubjects(@PathParam("grade") Integer grade);

    @GET
    @Path("/grade/{grade}/subject/{subject}")
    public String getSubjectTopics(@PathParam("grade") Integer grade,
                                   @PathParam("subject") String subject);

    @GET
    @Path("/grade/{grade}/subject/{subject}/topic/{topic}")
    public String getTopicContent(@PathParam("grade") Integer grade,
                                  @PathParam("subject") String subject,
                                  @PathParam("topic") String topic);
}