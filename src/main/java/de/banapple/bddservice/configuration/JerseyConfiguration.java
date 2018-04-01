package de.banapple.bddservice.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import de.banapple.bddservice.controller.StepsController;
import de.banapple.bddservice.controller.StoryController;

@Component
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {

        register(StepsController.class);
        register(StoryController.class);
    }
}
