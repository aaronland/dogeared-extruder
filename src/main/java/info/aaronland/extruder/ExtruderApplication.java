package info.aaronland.extruder;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.forms.MultiPartBundle;

import info.aaronland.extruder.ExtruderConfiguration;
    
import java.net.URL;
import java.util.Map;

public class ExtruderApplication extends Application<ExtruderConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExtruderApplication().run(args);
    }

    public void initialize(Bootstrap<ExtruderConfiguration> bootstrap) {
	
	bootstrap.addBundle(new MultiPartBundle());
	
	bootstrap.addBundle(new ViewBundle<ExtruderConfiguration>(){

		@Override
		public Map<String, Map<String, String>> getViewConfiguration(ExtruderConfiguration config) {
		    return config.getViewRendererConfiguration();
		}
	    });
    }

    @Override
	public void run(ExtruderConfiguration conf, Environment env) throws Exception {
	
        env.jersey().register(new BoilerpipeResource());
        env.jersey().register(new TikaResource());
        env.jersey().register(new JavaReadabilityResource());

	// TODO: put me in the config file... (20130908/straup)
	URL healthcheck_url = new URL("https://github.com/aaronland/dogeared-extruder/");
	
	env.healthChecks().register("internets", new InternetsHealthCheck(healthcheck_url));
    }

}
