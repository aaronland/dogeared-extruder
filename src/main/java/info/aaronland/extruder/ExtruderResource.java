package info.aaronland.extruder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.HTMLHighlighter;

@Path(value = "/extrude")
@Produces(MediaType.TEXT_PLAIN)
public class ExtruderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtruderResource.class);

    /*
    final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
    
    final boolean includeImages = true;
    final boolean bodyOnly = false;
    final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance(includeImages, bodyOnly);
    */

    @GET
    public String extrudeThis(@QueryParam("id") String id){

	LOGGER.info("GET ME " + id);

	/*
	URL url = new URL("");
	String extractedHtml = hh.process(url, extractor);
	*/

	return new String("HELLO");
    }

}
