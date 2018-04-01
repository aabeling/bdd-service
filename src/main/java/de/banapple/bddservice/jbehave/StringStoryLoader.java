package de.banapple.bddservice.jbehave;

import org.jbehave.core.io.StoryLoader;

public class StringStoryLoader implements StoryLoader {

    private String story;

    public StringStoryLoader(String story) {
        
        this.story = story;
    }
    
    @Override
    public String loadResourceAsText(String resourcePath) {

        return story;
    }

    @Override
    public String loadStoryAsText(String storyPath) {

        return story;
    }

}
