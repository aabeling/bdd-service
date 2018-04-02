package de.banapple.bddservice.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;
import org.springframework.stereotype.Component;

import de.banapple.bddservice.beans.StoryExecutionRequest;
import de.banapple.bddservice.beans.StoryExecutionResponse;
import de.banapple.bddservice.jbehave.CollectingStoryReporter;
import de.banapple.bddservice.jbehave.StringStoryLoader;
import de.banapple.bddservice.jbehave.steps.TraderSteps;

@Component
@Path("/stories")
public class StoryController {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public StoryExecutionResponse executeStory(
            StoryExecutionRequest request) {

        CollectingStoryReporter reporter = new CollectingStoryReporter();
        Configuration configuration = jbehaveConfiguration(
                request.getStoryLines(),
                reporter);

        EmbedderControls embedderControls = new EmbedderControls();
        embedderControls.doGenerateViewAfterStories(false);
        embedderControls.doIgnoreFailureInStories(true);
        
        Embedder embedder = new Embedder();
        embedder.useConfiguration(configuration);
        embedder.useStepsFactory(stepsFactory(configuration));
        embedder.useEmbedderControls(embedderControls);
        embedder.runStoriesAsPaths(Arrays.asList(request.getId()));

        StoryExecutionResponse response = new StoryExecutionResponse();
        response.setId(request.getId());
        response.setItems(reporter.getItems());
        
        return response;
    }

    private Configuration jbehaveConfiguration(
            final String[] storyLines,
            final StoryReporter reporter) {

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        ParameterConverters parameterConverters = new ParameterConverters();

        String story = StringUtils.join(storyLines,"\n");
        
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
                        .withReporters(reporter)
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
