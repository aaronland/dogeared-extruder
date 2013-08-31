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
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

@Path(value = "/tika")
@Produces("text/html; charset=UTF-8")
public class TikaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TikaResource.class);

    @GET
    public Response extrudeThis(@QueryParam("link") String link){

	URL url = null;
	String text = null;

	try {
	    url = new URL(link);
	}
	
	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
	}

	BufferedInputStream buffer = null;

	try {
	    URLConnection conn = url.openConnection();
	    buffer = new BufferedInputStream(conn.getInputStream());
	}

	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	try {

	    // https://gist.github.com/kinjouj/2507727

	    Parser parser = new AutoDetectParser();
	    ContentHandler handler = new BodyContentHandler();
	    
	    Metadata metadata = new Metadata();
 
	    parser.parse(buffer, handler, metadata, new ParseContext());

	    text = handler.toString();

	    // I suppose there is a way to do this without creating an
	    // object first?

	    TextUtils utils = new TextUtils();
	    text = utils.unwrap(text);
	    text = utils.text2html(text);
	}

	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

}
