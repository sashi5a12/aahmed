package com.sample.ch04;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MultivaluedMap;

@Path("/loginservice/")
@Consumes("application/x-www-form-urlencoded")
public interface AccessManager {

    @POST
    @Path("/userName/{userName}")
    public void postUserName(@FormParam("userName")String userName);

    @POST
    @Path("/userName/{userName}/password/{password}")
    public void postUserData(@FormParam("userName")String userName, @FormParam("password")String password);
/*
    //Use one method, based on the need.    
    @POST
    @Path("/userName/{userName}/password/{password}")
    public void postUserData(MultivaluedMap<String, String> formParams);
*/
}
