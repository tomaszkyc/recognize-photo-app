
package com.tomaszkyc.app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "categories",
    "description",
    "requestId",
    "metadata",
    "color"
})
public class ImageInformation {

    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("color")
    private Color color;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ImageInformation() {
    }

    /**
     * 
     * @param color
     * @param requestId
     * @param description
     * @param categories
     * @param metadata
     */
    public ImageInformation(List<Category> categories, Description description, String requestId, Metadata metadata, Color color) {
        super();
        this.categories = categories;
        this.description = description;
        this.requestId = requestId;
        this.metadata = metadata;
        this.color = color;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("color")
    public Color getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(Color color) {
        this.color = color;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("categories", categories).append("description", description).append("requestId", requestId).append("metadata", metadata).append("color", color).append("additionalProperties", additionalProperties).toString();
    }

}
