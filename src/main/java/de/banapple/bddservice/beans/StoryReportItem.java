package de.banapple.bddservice.beans;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StoryReportItem {

    private String type;
    private String step;
    private String cause;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public StoryReportItem withType(String type) {
        
        this.type = type;
        return this;
    }
    
    public StoryReportItem withStep(String step) {
        
        this.step = step;
        return this;
    }
    
    public StoryReportItem withCause(Throwable cause) {
         
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);
        cause.printStackTrace(writer);
        this.cause = stringWriter.toString();
        return this;
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
