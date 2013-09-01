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
    private String text;

    public Document(URL _uri, String _text){
	uri = _uri;
	text = _text;
    }

    public String getURI(){
	return this.uri.toString();
    }

    public String getText(){
	return this.text;
    }

    public ArrayList<String> getBlocks(){

	String text = this.getText();
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

    public String toJSON(){
	return "PLEASE WRITE ME";
    }
}
