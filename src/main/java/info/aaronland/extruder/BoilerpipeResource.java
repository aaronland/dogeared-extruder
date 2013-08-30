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

import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import org.apache.commons.lang3.StringEscapeUtils;

@Path(value = "/boilerpipe")
@Produces(MediaType.TEXT_HTML)
public class BoilerpipeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoilerpipeResource.class);

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

	try {
	    text = ArticleExtractor.INSTANCE.getText(url);
	    text = text2html(text);
	}

	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

    private String text2html(String text){

	String html = "";
	String[] paras = text.split("[\n\n]+");

	Integer count = paras.length;
	LOGGER.info(count + " paras");
	
	for (String p : paras){
	    html = html + "<p>" + StringEscapeUtils.escapeXml(p) + "</p>";
	}

	return html;
    }

}
