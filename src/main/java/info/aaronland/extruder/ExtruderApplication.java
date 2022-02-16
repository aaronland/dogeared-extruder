package info.aaronland.extruder;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import info.aaronland.extruder.ExtruderConfiguration;

import java.net.URL;

public class ExtruderApplication extends Application<ExtruderConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExtruderApplication().run(args);
    }

    @Override
	public void initialize(Bootstrap<ExtruderConfiguration> bootstrap) {
        // bootstrap.setName("extruder");
	bootstrap.addBundle(new ViewBundle());
    }

    @Override
	public void run(ExtruderConfiguration conf, Environment env) throws Exception {
        env.jersey().register(new BoilerpipeResource());
        env.jersey().register(new TikaResource());
        env.jersey().register(new JavaReadabilityResource());

	// TODO: put me in the config file... (20130908/straup)
	URL healthcheck_url = new URL("http://collection.cooperhewitt.org/objects/random/");
	env.addHealthCheck(new InternetsHealthCheck(healthcheck_url));
    }

}
