package de.banapple.bddservice.jbehave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jbehave.core.reporters.NullStoryReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.banapple.bddservice.beans.StoryReportItem;

public class CollectingStoryReporter extends NullStoryReporter {

    private static final Logger LOG = LoggerFactory.getLogger(CollectingStoryReporter.class);

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
        addItem(item);
    }

    private void addItem(String method, String step) {

        StoryReportItem item = new StoryReportItem()
                .withType(method)
                .withStep(step);
        addItem(item);
    }

    private void addItem(StoryReportItem item) {

        item.setDate(new Date());
        LOG.info("adding item: {}", item);
        items.add(item);
    }

    public List<StoryReportItem> getItems() {

        return items;
    }
}
