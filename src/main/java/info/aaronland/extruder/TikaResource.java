package info.aaronland.extruder;

import info.aaronland.extruder.Document;
import info.aaronland.extruder.DocumentView;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;

import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import org.apache.commons.io.FilenameUtils;

@Path(value = "/tika")
@Produces({MediaType.TEXT_HTML + "; charset=UTF-8", MediaType.APPLICATION_JSON})
public class TikaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TikaResource.class);
    
    @GET
    public Response extrudeThisUrl(@QueryParam("url") String uri){

	URL url;
	Document doc;
	DocumentView view;

	try {
	    url = new URL(uri);
	}
	
	catch (Exception e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.toString()).build();
	}

	BufferedInputStream buffer = null;

	try {
	    URLConnection conn = url.openConnection();
	    buffer = new BufferedInputStream(conn.getInputStream());
	}

	catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}
	
	try {
	    doc = extrudeThis(buffer);
	    view = new DocumentView(doc);
	}

	catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	return Response.status(Response.Status.OK).entity(view).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(FormDataMultiPart formParams){

	FormDataBodyPart stream = formParams.getField("file");
	InputStream upload = stream.getValueAs(InputStream.class);

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	Document doc;
	DocumentView view;

	try {
	    doc = extrudeThis(buffer);
	    view = new DocumentView(doc);
	}

	catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	return Response.status(Response.Status.OK).entity(view).build();
    }

    // TO DO: figure out how to make this return HTML instead of text
    // (20130831/straup)

    // I have no idea how that would square with the Document class...
    // (20130901/straup)

    private Document extrudeThis(InputStream buffer){
	    
	String text;
	String title;

	// http://stackoverflow.com/questions/6144708/apache-tika-and-character-limit-when-parsing-documents 

	Parser parser = new AutoDetectParser();
	ContentHandler handler = new BodyContentHandler();	   
	Metadata metadata = new Metadata();

	try {
	    parser.parse(buffer, handler, metadata, new ParseContext());
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}

	text = handler.toString();
	text = unwrapText(text);

	title = metadata.get(TikaCoreProperties.TITLE);

	if (title == null){

	    String type = "mystery";

	    try {
		String content_type = metadata.get(TikaCoreProperties.CONTENT_TYPE_HINT);
		String[] parts = content_type.split("/");	    
		type = parts[1];
	    }

	    catch (Exception e){
		LOGGER.info("Failed to parse content type because " + e.toString());
	    }

	    title = "Untitled " + type.toUpperCase() + " Document #" + System.currentTimeMillis();
	}

	return new Document(text, title);
    }

    // Not awesome. No. (20130903/straup)

    private static String unwrapText(String text){

	String[] raw = text.split(System.getProperty("line.separator"));

	List<String> paras = new ArrayList<String>();
	String buffer = "";
	
	for (String ln : raw){

	    ln = ln.trim();

	    if (ln.equals("")){

		if (buffer.length() > 0){
		    paras.add(buffer);
		}

		buffer = "";
	    }
	    
	    else {
		buffer = buffer + " " + ln;
	    }
	}

	if (buffer.length() > 0){
	    paras.add(buffer);
	}

	// why you hate "join" so much Java?
	// (20130831/straup)

	StringBuilder sb = new StringBuilder();

	for (Object obj : paras) {
	    sb.append(obj.toString());
	    sb.append("\n");
	}

	return sb.toString();
    }

}
