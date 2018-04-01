package de.banapple.bddservice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;
import org.jbehave.core.steps.StepCandidate;
import org.springframework.stereotype.Component;

import de.banapple.bddservice.beans.Container;
import de.banapple.bddservice.beans.Step;
import de.banapple.bddservice.beans.Step.StepType;
import de.banapple.bddservice.jbehave.StringStoryLoader;
import de.banapple.bddservice.jbehave.steps.TraderSteps;

@Component
@Path("/steps")
public class StepsController {

    @GET
    @Produces("application/json")
    public Container<Step> getAllSteps() {

        InjectableStepsFactory stepsFactory = stepsFactory(jbehaveConfiguration(""));
        
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
    
    private Configuration jbehaveConfiguration(String story) {

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        ParameterConverters parameterConverters = new ParameterConverters();

        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(
                new LocalizedKeywords(),
                new LoadFromClasspath(this.getClass()),
                parameterConverters,
                new ParameterControls(),
                new TableTransformers());

        parameterConverters.addConverters(
                new DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));

        return new MostUsefulConfiguration()
                .useStoryControls(new StoryControls()
                        .doDryRun(false)
                        .doSkipScenariosAfterFailure(false))
                .useStoryLoader(new StringStoryLoader(story))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStoryReporterBuilder(new StoryReporterBuilder()                        
                        .withDefaultFormats()                        
                        .withViewResources(viewResources)
                        .withFormats(Format.JSON, Format.CONSOLE)
                        .withFailureTrace(true)
                        .withFailureTraceCompression(true))
                .useParameterConverters(parameterConverters)
                .useStepPatternParser(new RegexPrefixCapturingPatternParser("$"));
    }

    private InjectableStepsFactory stepsFactory(Configuration configuration) {

        return new InstanceStepsFactory(
                configuration,
                new TraderSteps());
    }
}
