package info.aaronland.extruder;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.util.UUID;

public class UploadUtils {

    public File inputStreamToTempFile(InputStream is){

	String tempdir = System.getProperty("java.io.tmpdir");
	String tempname = UUID.randomUUID().toString();
	    
	String path = tempdir + "/" + tempname;

	File file = new File(path);

	try {

	    FileOutputStream out = new FileOutputStream(file);

	    int read = 0;
	    byte[] bytes = new byte[1024];
 
	    while ((read = is.read(bytes)) != -1){
		out.write(bytes, 0, read);
	    }

	    out.flush();
	    out.close();
	}

	catch (Exception e){
	    deleteFile(file);
	    throw new RuntimeException(e);
	}

	return file;
    }

    public void deleteFile(File file){

	if (! file.exists()){
	    return;
	}

	String path = file.getAbsolutePath();

	try {
	    file.delete();
	}

	catch (Exception e){
	    throw new RuntimeException(e);
	}
    }

}
