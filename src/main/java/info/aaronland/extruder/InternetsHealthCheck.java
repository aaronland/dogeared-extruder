package info.aaronland.extruder;

import com.codahale.metrics.health.HealthCheck;

import java.net.URL;
import java.net.HttpURLConnection;

public class InternetsHealthCheck extends HealthCheck {

    private URL url;

    public InternetsHealthCheck(URL url) {
        super();
	this.url = url;
    }

    @Override
    protected Result check() throws Exception {

	int statusCode;

	try {

	    HttpURLConnection http = (HttpURLConnection)this.url.openConnection();
	    statusCode = http.getResponseCode();
	}

	catch (Exception e){
	    return Result.unhealthy("Failed to retrive " + this.url.toString() + " because " + e.toString());
	}

	if (statusCode != 200){
	    return Result.unhealthy(this.url.toString() + " returned status code " + statusCode);
	}
	
        return Result.healthy();
    }
}
