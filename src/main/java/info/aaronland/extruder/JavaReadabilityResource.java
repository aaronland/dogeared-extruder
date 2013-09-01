package info.aaronland.extruder;

import info.aaronland.extruder.UploadUtils;
import info.aaronland.extruder.TextUtils;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import java.io.InputStream;
import java.io.File;

import java.net.URL;

import com.basistech.readability.Readability;

// See below inre: Readers (20130901/straup)
import com.basistech.readability.HttpPageReader;
import com.basistech.readability.FilePageReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(value = "/java-readability")
@Produces("text/html; charset=UTF-8")
public class JavaReadabilityResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaReadabilityResource.class);

    @GET
    public Response extrudeThisURL(@QueryParam("link") String uri){

	String text = "";

	try {
	    text = extrudeThis(uri);
	}

	// TODO: trap MalformedURLExceptions and return NOT_ACCEPTABLE here (20130901/straup)

	catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	return Response.status(Response.Status.OK).entity(text).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(@FormDataParam("file") InputStream upload){

	// throw new RuntimeException("Java-readability needs to be taught to love local files");

	UploadUtils up_utils = new UploadUtils();

	File file = up_utils.inputStreamToTempFile(upload);

	String uri = "file://" + file.getAbsolutePath();
	String text = "";

	try {
	    text = extrudeThis(uri);
	}

	catch (Exception e){
	    up_utils.deleteFile(file);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
	}

	up_utils.deleteFile(file);

	return Response.status(Response.Status.OK).entity(text).build();
    }

    private String extrudeThis(String uri){

	URL url = null;
	String text = "";

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
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}
	
	return text;
    }
}
