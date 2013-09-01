package info.aaronland.extruder;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;
// import org.apache.commons.lang3.text.WordUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Document {

    private static final Logger LOGGER = LoggerFactory.getLogger(Document.class);

    private URL uri;
    private ArrayList<String> blocks;

    // TO DO: make URI an optional parameter

    public Document(String text){

	// Something about this causes Jackson (?) to complete lose its
	// shit when it tries to serialize it as a blob of JSON. As in:
	// return Response.status(Response.Status.OK).entity(doc).build();
	// (20130901/straup)

	blocks = parseText(text);
    }

    public String getURI(){
	return this.uri.toString();
    }

    public ArrayList<String> getBlocks(){
	return this.blocks;
    }

    public String toString(){

	ArrayList<String> blocks = this.getBlocks();

	StringBuilder sb = new StringBuilder();

	for (Object obj : blocks) {
	    sb.append(obj.toString());
	    sb.append("\n\n");
	}

	return sb.toString();
    }

    public String toHTML(){

	ArrayList<String> blocks = this.getBlocks();

	StringBuilder sb = new StringBuilder();

	for (Object obj : blocks) {
	    String html = "<p>" + StringEscapeUtils.escapeXml(obj.toString()) + "</p>";
	    sb.append(html);
	}

	return sb.toString();
    }

    private static ArrayList<String> parseText(String text){

	String[] raw = text.split(System.getProperty("line.separator"));

	ArrayList<String> blocks = new ArrayList<String>();
	String buffer = "";
	
	for (String ln : raw){

	    ln = ln.trim();

	    if (ln.equals("")){

		if (buffer.length() > 0){
		    blocks.add(buffer);
		}

		buffer = "";
	    }
	    
	    else {
		buffer = buffer + " " + ln;
	    }
	}

	if (buffer.length() > 0){
	    blocks.add(buffer);
	}

	return blocks;
    }

}
