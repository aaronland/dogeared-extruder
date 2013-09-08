package info.aaronland.extruder;

//import com.yammer.metrics.core.HealthCheck;
import com.codahale.metrics.health.HealthCheck;

public class InternetsHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
	// return Result.unhealthy("template doesn't include a name");
        return Result.healthy();
    }
}
