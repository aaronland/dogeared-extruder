package info.aaronland.extruder;

import info.aaronland.extruder.TextUtils;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;

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

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

@Path(value = "/boilerpipe")
@Produces("text/html; charset=UTF-8")
public class BoilerpipeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoilerpipeResource.class);

    @GET
    public Response extrudeThisURL(@QueryParam("link") String uri){

	String text = "";

	try {
	    text = extractThis(uri);
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}
	
	TextUtils utils = new TextUtils();
	//text = utils.unwrap(text);
	text = utils.text2html(text);

	return Response.status(Response.Status.OK).entity(text).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extrudeThisFile(@FormDataParam("file") InputStream upload){

	// sudo put me in a function

	String tempdir = System.getProperty("java.io.tmpdir");
	String tempname = UUID.randomUUID().toString();
	    
	String path = tempdir + "/" + tempname;

	File file = new File(path);

	try {

	    FileOutputStream out = new FileOutputStream(file);

	    int read = 0;
	    byte[] bytes = new byte[1024];
 
	    while ((read = upload.read(bytes)) != -1){
		out.write(bytes, 0, read);
	    }

	    out.flush();
	    out.close();
	}

	catch (Exception e){
	    deleteFile(file);
	    throw new RuntimeException(e);
	}

	String uri = "file://" + file.getAbsolutePath();
	String text = "";

	try {
	    text = extractThis(uri);
	}

	catch (Exception e){
	    deleteFile(file);
	    throw new RuntimeException(e);
	}

	deleteFile(file);

	return Response.status(Response.Status.OK).entity(text).build();
    }

    private String extractThis(String uri){

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

    private void deleteFile(File file){

	if (! file.exists()){
	    return;
	}

	String path = file.getAbsolutePath();

	try {
	    file.delete();
	    LOGGER.info("Deleted " + path);
	}

	catch (Exception e){
	    LOGGER.error("Failed to delete " + path + " because: " + e.toString());
	}
    }

}
