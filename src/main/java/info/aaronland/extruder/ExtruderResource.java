package info.aaronland.extruder;

// import info.aaronland.extruder.ExtruderConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/extrude")
@Produces(MediaType.TEXT_PLAIN)
public class ExtruderResource {

    public ExtruderResource(){

    }

    @GET
    public String extrudeThis(){
	return new String("HELLO");
    }

}
