package info.aaronland.extruder;

import info.aaronland.extruder.TextUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import java.net.URL;

import com.basistech.readability.Readability;
import com.basistech.readability.HttpPageReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(value = "/java-readability")
@Produces("text/html; charset=UTF-8")
public class JavaReadabilityResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaReadabilityResource.class);

    @GET
    public Response extrudeThis(@QueryParam("link") String link){

	URL url = null;
	String text = null;

	try {
	    url = new URL(link);
	}
	
	catch (Exception e){
	    LOGGER.error("URL ERROR " + e.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
	}

	try {

	    Readability parser = new Readability();
	    HttpPageReader reader = new HttpPageReader();

	    parser.setPageReader(reader);
	    parser.processDocument(link);

	    text = parser.getArticleText();

	    TextUtils utils = new TextUtils();
	    text = utils.text2html(text);
	}

	catch (Exception e){
	    LOGGER.error("PARSE ERROR " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

}
