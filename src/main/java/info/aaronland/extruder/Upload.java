package info.aaronland.extruder;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Upload {

    private static final Logger LOGGER = LoggerFactory.getLogger(Upload.class);

    private String path;

    public Upload(){

	// TO DO: sort out file extensions etc.
	// (20130901/straup)

	String tmpdir = System.getProperty("java.io.tmpdir");
	String tmpname = UUID.randomUUID().toString();
	    
	path = tmpdir + "/" + tmpname;
    }

    public File writeTmpFile(InputStream input){

	File tmpfile = new File(this.path);

	try {

	    FileOutputStream out = new FileOutputStream(tmpfile);

	    int read = 0;
	    byte[] bytes = new byte[1024];
 
	    while ((read = input.read(bytes)) != -1){
		out.write(bytes, 0, read);
	    }

	    out.flush();
	    out.close();
	}

	catch (Exception e){
	    //tmpfile.delete();
	    throw new RuntimeException(e);
	}

	return tmpfile;
    }

}
