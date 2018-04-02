package de.banapple.bddservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.StepCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.banapple.bddservice.beans.Container;
import de.banapple.bddservice.beans.Step;
import de.banapple.bddservice.beans.Step.StepType;
import de.banapple.bddservice.jbehave.StepsProvider;

@Component
@Path("/steps")
public class StepsController {

    @Autowired
    StepsProvider stepsProvider;
    
    @GET
    @Produces("application/json")
    public Container<Step> getAllSteps() {

        InjectableStepsFactory stepsFactory = stepsFactory();

        List<CandidateSteps> candidates = stepsFactory.createCandidateSteps();
        List<StepCandidate> stepCandidates = new ArrayList<>();
        candidates.stream()
                .map(cs -> cs.listCandidates())
                .forEach(list -> {
                    list.forEach(c -> stepCandidates.add(c));
                });

        List<Step> result = stepCandidates.stream()
                .map(this::mapStep)
                .collect(Collectors.toList());

        return new Container<Step>(result);

    }

    private Step mapStep(StepCandidate stepCandidate) {

        Step step = new Step();
        step.setValue(stepCandidate.getPatternAsString());
        step.setType(StepType.valueOf(stepCandidate.getStepType().name()));

        return step;
    }

    private InjectableStepsFactory stepsFactory() {

        return new InstanceStepsFactory(
                new MostUsefulConfiguration(),
                stepsProvider.getStepsInstances());
    }
}
