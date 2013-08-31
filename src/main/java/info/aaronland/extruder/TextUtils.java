package info.aaronland.extruder;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

import org.apache.commons.lang3.StringEscapeUtils;
// import org.apache.commons.lang3.text.WordUtils;

public class TextUtils {

    // please do not pretend that I think this is good or elegant
    // (20130831/straup)

    public String unwrap(String text){

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

	// why you hate "join" so much Java?
	// (20130831/straup)

	StringBuilder sb = new StringBuilder();

	for (Object obj : paras) {
	    sb.append(obj.toString());
	    sb.append("\n\n");
	}

	return sb.toString();
    }

    public String text2html(String text){

	String html = "";
	String[] paras = text.split("[\n\n]+");

	Integer count = paras.length;
	
	for (String p : paras){
	    html = html + "<p>" + StringEscapeUtils.escapeXml(p) + "</p>";
	}

	return html;
    }

}
