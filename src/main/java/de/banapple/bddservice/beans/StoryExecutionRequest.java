package de.banapple.bddservice.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StoryExecutionRequest {

    private String id;
    private String[] storyLines;

    public StoryExecutionRequest() {
        
        /* intentionally left blank */
    }
    
    public StoryExecutionRequest(String id, String... storyLines) {
        
        this.id = id;
        this.storyLines = storyLines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getStoryLines() {
        return storyLines;
    }

    public void setStoryLines(String[] storyLines) {
        this.storyLines = storyLines;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
