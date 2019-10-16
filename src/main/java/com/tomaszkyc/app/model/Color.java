
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
    "dominantColorForeground",
    "dominantColorBackground",
    "dominantColors",
    "accentColor",
    "isBWImg"
})
public class Color {

    @JsonProperty("dominantColorForeground")
    private String dominantColorForeground;
    @JsonProperty("dominantColorBackground")
    private String dominantColorBackground;
    @JsonProperty("dominantColors")
    private List<String> dominantColors = null;
    @JsonProperty("accentColor")
    private String accentColor;
    @JsonProperty("isBWImg")
    private Boolean isBWImg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Color() {
    }

    /**
     * 
     * @param dominantColors
     * @param dominantColorBackground
     * @param dominantColorForeground
     * @param accentColor
     * @param isBWImg
     */
    public Color(String dominantColorForeground, String dominantColorBackground, List<String> dominantColors, String accentColor, Boolean isBWImg) {
        super();
        this.dominantColorForeground = dominantColorForeground;
        this.dominantColorBackground = dominantColorBackground;
        this.dominantColors = dominantColors;
        this.accentColor = accentColor;
        this.isBWImg = isBWImg;
    }

    @JsonProperty("dominantColorForeground")
    public String getDominantColorForeground() {
        return dominantColorForeground;
    }

    @JsonProperty("dominantColorForeground")
    public void setDominantColorForeground(String dominantColorForeground) {
        this.dominantColorForeground = dominantColorForeground;
    }

    @JsonProperty("dominantColorBackground")
    public String getDominantColorBackground() {
        return dominantColorBackground;
    }

    @JsonProperty("dominantColorBackground")
    public void setDominantColorBackground(String dominantColorBackground) {
        this.dominantColorBackground = dominantColorBackground;
    }

    @JsonProperty("dominantColors")
    public List<String> getDominantColors() {
        return dominantColors;
    }

    @JsonProperty("dominantColors")
    public void setDominantColors(List<String> dominantColors) {
        this.dominantColors = dominantColors;
    }

    @JsonProperty("accentColor")
    public String getAccentColor() {
        return accentColor;
    }

    @JsonProperty("accentColor")
    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    @JsonProperty("isBWImg")
    public Boolean getIsBWImg() {
        return isBWImg;
    }

    @JsonProperty("isBWImg")
    public void setIsBWImg(Boolean isBWImg) {
        this.isBWImg = isBWImg;
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
        return new ToStringBuilder(this).append("dominantColorForeground", dominantColorForeground).append("dominantColorBackground", dominantColorBackground).append("dominantColors", dominantColors).append("accentColor", accentColor).append("isBWImg", isBWImg).append("additionalProperties", additionalProperties).toString();
    }

}
