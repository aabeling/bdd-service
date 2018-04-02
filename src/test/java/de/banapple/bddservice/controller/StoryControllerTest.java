package de.banapple.bddservice.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import de.banapple.bddservice.beans.StoryExecutionRequest;
import de.banapple.bddservice.jbehave.StepsProvider;

public class StoryControllerTest {

    private StoryController controller;

    @Before
    public void setUp() {
        
        controller = new StoryController();
        controller.stepsProvider = new StepsProvider();
    }
    
    @Test
    public void test_executeStory_successfulStory() throws IOException {
        
        String story = IOUtils.toString(
                this.getClass().getResourceAsStream("/stories/example1.story"), Charset.defaultCharset());
        
        controller.executeStory(new StoryExecutionRequest("example1", story));
    }
    
    @Test
    public void test_executeStory_failingStory() throws IOException {
        
        String story = IOUtils.toString(
                this.getClass().getResourceAsStream("/stories/example2.story"), Charset.defaultCharset());
        
        controller.executeStory(new StoryExecutionRequest("example2", story));
    }
}
