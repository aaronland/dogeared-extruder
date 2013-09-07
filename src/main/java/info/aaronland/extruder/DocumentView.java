package info.aaronland.extruder;

import info.aaronland.extruder.Document;
import com.yammer.dropwizard.views.View;

public class DocumentView extends View {
    private final Document document;

    public DocumentView(Document document){
        super("document.ftl");
        this.document = document;
    }

    public Document getDocument(){
        return document;
    }

}
