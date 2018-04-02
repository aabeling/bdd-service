package de.banapple.bddservice.jbehave;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import de.banapple.bddservice.jbehave.steps.LongRunningSteps;
import de.banapple.bddservice.jbehave.steps.TraderSteps;

/**
 * Returns the classes implementing the steps.
 */
@Component
public class StepsProvider {

    public List<?> getStepsInstances() {
        
        return Arrays.asList(
                new TraderSteps(),
                new LongRunningSteps());
    }
}
