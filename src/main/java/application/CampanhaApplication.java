package application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class CampanhaApplication extends ResourceConfig {
	
	public CampanhaApplication() {
		// Register resources and providers using package-scanning.
        packages("resource");
        
        // Register JSON handler
        register(JacksonFeature.class);
	}

}
