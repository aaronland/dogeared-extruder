package info.aaronland.extruder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(value = "/tika")
@Produces(MediaType.TEXT_PLAIN)
public class TikaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TikaResource.class);

    @GET
    public Response extrudeThis(@QueryParam("link") String link){

	LOGGER.info("GET ME " + link);

	URL url = null;
	String text = null;

	try {
	    url = new URL(link);
	}
	
	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
	}

	
	/*
	try {

	}

	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	*/

	return Response.status(Response.Status.OK).entity(text).build();
    }

}