package info.aaronland.extruder;

import info.aaronland.extruder.Document;
import com.yammer.dropwizard.views.View;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import java.nio.charset.Charset;

public class DocumentView extends View {
    private final Document document;

    public DocumentView(Document document){
        super("document.ftl");
        this.document = document;
    }

    public Document getDocument(){
        return document;
    }

    // Because in com/codahale/dropwizard/views/freemarker/FreemarkerViewRenderer.java this:
    // final Charset charset = view.getCharset().or(Charset.forName(configuration.getEncoding(locale)));
    // And since the default encoding for en-us is ISO-8859-1... good times
    // (20130908/straup)

    public Optional<Charset> getCharset(){
	return Optional.of(Charsets.UTF_8);
    }

}
