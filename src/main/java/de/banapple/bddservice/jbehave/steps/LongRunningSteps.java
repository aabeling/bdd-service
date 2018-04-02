package de.banapple.bddservice.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Some example steps which take a long time to run.
 */
public class LongRunningSteps {

    @Given("some short operation")
    public void someShortOperation() {
        
        /* intentionally left blank */
    }
    
    @When("some long operation")
    public void someLongOperation() {
        
        sleep(10_000);
    }
    

    @Then("another long operation")
    public void anotherLongOperation() {
        
        sleep(10_000);
    }
    
    private void sleep(long delay) {
        
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            /* intentionally left blank */
        }
    }
}
