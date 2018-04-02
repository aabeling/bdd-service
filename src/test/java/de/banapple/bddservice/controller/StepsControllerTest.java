package de.banapple.bddservice.controller;

import de.banapple.bddservice.beans.Container;
import de.banapple.bddservice.beans.Step;
import static org.junit.Assert.*;

import org.junit.Test;

public class StepsControllerTest {

    @Test
    public void test_getAllSteps() {
        
        StepsController controller = new StepsController();
        
        Container<Step> container = controller.getAllSteps();
        assertNotNull(container);
        assertNotNull(container.getData());
        assertFalse(container.getData().isEmpty());
    }
}
