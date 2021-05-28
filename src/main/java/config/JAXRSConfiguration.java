package config;

import vocabulario.Vocabulario;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import resources.JavaEE8Resource;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("/")
public class JAXRSConfiguration extends Application {    
}
