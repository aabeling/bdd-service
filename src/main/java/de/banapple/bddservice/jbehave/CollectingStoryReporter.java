package de.banapple.bddservice.jbehave;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.reporters.NullStoryReporter;

import de.banapple.bddservice.beans.StoryReportItem;

public class CollectingStoryReporter extends NullStoryReporter {

    private List<StoryReportItem> items;

    public CollectingStoryReporter() {
    
        items = new ArrayList<>();
    }
    
    @Override
    public void successful(String step) {
        
        addItem("successful", step);
    }

    @Override
    public void pending(String step) {
        
        addItem("pending", step);
    }

    @Override
    public void notPerformed(String step) {

        addItem("notPerformed", step);
    }

    @Override
    public void failed(String step, Throwable cause) {
        
        addItem("failed", step, cause);
    }

    private void addItem(String method, String step, Throwable cause) {
        
        StoryReportItem item = new StoryReportItem()
                .withType(method)
                .withStep(step)
                .withCause(cause);
        items.add(item);
    }

    private void addItem(String method, String step) {
        
        StoryReportItem item = new StoryReportItem()
                .withType(method)
                .withStep(step);
        items.add(item);
    }

    public List<StoryReportItem> getItems() {

        return items;
    }
}
