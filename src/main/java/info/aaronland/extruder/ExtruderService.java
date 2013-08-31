package info.aaronland.extruder;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import info.aaronland.extruder.ExtruderConfiguration;
import info.aaronland.extruder.BoilerpipeResource;

public class ExtruderService extends Service<ExtruderConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExtruderService().run(args);
    }

    @Override
	public void initialize(Bootstrap<ExtruderConfiguration> bootstrap) {
        bootstrap.setName("extruder");
    }

    @Override
	public void run(ExtruderConfiguration conf, Environment env) throws Exception {
        env.addResource(new BoilerpipeResource());
        env.addResource(new TikaResource());
        env.addResource(new JavaReadabilityResource());
    }

}
