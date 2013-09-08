package info.aaronland.extruder;

import info.aaronland.extruder.Upload;
import info.aaronland.extruder.Document;
import info.aaronland.extruder.DocumentView;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataBodyPart;

import java.io.InputStream;
import java.io.File;

import java.net.URL;

import com.basistech.readability.Readability;
import org.apache.commons.io.FilenameUtils;

// See below inre: Readers (20130901/straup)
import com.basistech.readability.HttpPageReader;
import com.basistech.readability.FilePageReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(value = "/java-readability")
@Produces({MediaType.TEXT_HTML + "; charset=UTF-8", MediaType.APPLICATION_JSON})
public class JavaReadabilityResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaReadabilityResource.class);

    @GET
    public Response extrudeThisURL(@QueryParam("url") String url){

	Document doc;
	DocumentView view;

	try {
	    doc = extrudeThis(url);
	    view = new DocumentView(doc);
	}

	// TODO: trap MalformedURLExceptions and return NOT_ACCEPTABLE here (20130901/straup)

	catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	return Response.status(Response.Status.OK).entity(view).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(FormDataMultiPart formParams){

	FormDataBodyPart stream = formParams.getField("file");
	InputStream input = stream.getValueAs(InputStream.class);

	Upload upload = new Upload();
	File tmpfile = upload.writeTmpFile(input);

	String uri = "file://" + tmpfile.getAbsolutePath();

	Document doc;
	DocumentView view;

	try {
	    doc = extrudeThis(uri);
	    view = new DocumentView(doc);
	}

	catch (Exception e){
	    tmpfile.delete();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	tmpfile.delete();

	return Response.status(Response.Status.OK).entity(view).build();
    }

    private Document extrudeThis(String uri){

	URL url;
	String text;
	String title;

	try {
	    url = new URL(uri);
	}
	
	catch (Exception e){
	    throw new RuntimeException(e);
	}

	try {
	    
	    Readability parser = new Readability();
	    String path = url.toString();

	    // Basically I need to write a URIPageReader class to hide
	    // all this nonsense because the HttpPageReader uses the Http
	    // classes rather than java.net.URL (20130901/straup)

	    if (path.startsWith("file:")){
		path = path.replace("file:", "");
		FilePageReader reader = new FilePageReader();
		parser.setPageReader(reader);
	    }

	    else {
		HttpPageReader reader = new HttpPageReader();
		parser.setPageReader(reader);
	    }

	    parser.processDocument(path);
	    text = parser.getArticleText();

	    title = FilenameUtils.getBaseName(url.toString());
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}       

	return new Document(text, title);
    }

}
