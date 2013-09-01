package info.aaronland.extruder;

import info.aaronland.extruder.TextUtils;
import info.aaronland.extruder.UploadUtils;

import java.io.InputStream;
import java.io.File;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

@Path(value = "/boilerpipe")
@Produces("text/html; charset=UTF-8")
public class BoilerpipeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoilerpipeResource.class);
    private static final TextUtils utils = new TextUtils();

    @GET
    public Response extrudeThisURL(@QueryParam("link") String uri){

	String text = "";

	try {
	    text = extrudeThis(uri);
	    text = massageText(text);
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}
	
	return Response.status(Response.Status.OK).entity(text).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(@FormDataParam("file") InputStream upload){

	UploadUtils up_utils = new UploadUtils();

	File file = up_utils.inputStreamToTempFile(upload);

	String uri = "file://" + file.getAbsolutePath();
	String text = "";

	try {
	    text = extrudeThis(uri);
	    text = massageText(text);
	}

	catch (Exception e){
	    up_utils.deleteFile(file);
	    throw new RuntimeException(e);
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

	
	LOGGER.info("GET " + url.toString());

	try {
	    text = ArticleExtractor.INSTANCE.getText(url);
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}

	return text;
    }

    private String massageText(String text){
	//text = utils.unwrap(text);
	text = utils.text2html(text);
	return text;
    }

}
