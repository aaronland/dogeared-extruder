package info.aaronland.extruder;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

import org.apache.commons.lang3.StringEscapeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Document {

    private static final Logger LOGGER = LoggerFactory.getLogger(Document.class);

    private ArrayList<String> blocks;

    // TO DO: make URI an optional parameter

    public Document(String text){
	blocks = parseText(text);
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

    // Please make me better and if possible make the unwrapText method
    // in TikaResource redundant... (20130903/straup)

    private static ArrayList<String> parseText(String text){

	String[] raw = text.split(System.getProperty("line.separator"));

	ArrayList<String> blocks = new ArrayList<String>();
	String buffer = "";
	
	for (String ln : raw){

	    ln = ln.trim();

	    blocks.add(ln);
	    buffer = "";
	}

	if (buffer.length() > 0){
	    blocks.add(buffer);
	}

	return blocks;
    }
}
