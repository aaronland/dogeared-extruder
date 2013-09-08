package info.aaronland.extruder;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import info.aaronland.extruder.ExtruderConfiguration;

import java.net.URL;

public class ExtruderService extends Service<ExtruderConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExtruderService().run(args);
    }

    @Override
	public void initialize(Bootstrap<ExtruderConfiguration> bootstrap) {
        bootstrap.setName("extruder");
	bootstrap.addBundle(new ViewBundle());
    }

    @Override
	public void run(ExtruderConfiguration conf, Environment env) throws Exception {
        env.addResource(new BoilerpipeResource());
        env.addResource(new TikaResource());
        env.addResource(new JavaReadabilityResource());

	// TODO: put me in the config file... (20130908/straup)
	URL healthcheck_url = new URL("http://collection.cooperhewitt.org/objects/random/");
	env.addHealthCheck(new InternetsHealthCheck(healthcheck_url));
    }

}
