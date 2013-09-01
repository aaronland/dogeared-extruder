package info.aaronland.extruder;

import info.aaronland.extruder.TextUtils;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final TextUtils utils = new TextUtils();

    @GET
    public Response extrudeThisUrl(@QueryParam("link") String link){

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
	    text = extrudeThis(buffer);
	    text = massageText(text);
	}

	catch (Exception e){
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(@FormDataParam("file") InputStream upload){

	// MOON LANGUAGE – if there's a better way to make it so that
	// Tika doesn't complain that the stream (upload) is already
	// closed I would love to hear about it... (20130831/straup)

	ByteArrayInputStream buffer = null;

	try {

	    ByteArrayOutputStream out = new ByteArrayOutputStream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = upload.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();

	    buffer = new ByteArrayInputStream(out.toByteArray());
	}

	catch (Exception e){
	    // throw new RuntimeException(e);
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	String text = "";

	try {
	    text = extrudeThis(buffer);
	    text = massageText(text);
	}

	catch (Exception e){
	    // throw new RuntimeException(e);
	    LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

    // TO DO: figure out how to make this return HTML instead of text
    // (20130831/straup)

    private String extrudeThis(InputStream buffer){
		
	Parser parser = new AutoDetectParser();
	ContentHandler handler = new BodyContentHandler();
	    
	Metadata metadata = new Metadata();

	try {
	    parser.parse(buffer, handler, metadata, new ParseContext());
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}

	return handler.toString();
    }

    private String massageText(String text){
	text = utils.unwrap(text);
	text = utils.text2html(text);
	return text;
    }
}
