package program.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Style {
    @JsonProperty("border-width")
    private String borderWidth;
    @JsonProperty("border-color")
    private String borderColor;
    @JsonProperty("border-opacity")
    private String borderOpacity;
    @JsonProperty("background-color")
    private String backgroundColor;
    @JsonProperty("shape")
    private String shape;
    @JsonProperty("height")
    private String height;
    @JsonProperty("width")
    private String width;
    @JsonProperty("line-color")
    private String lineColor;

    public Style () {}

    public Style (@JsonProperty("border-width") String borderWidth,
                  @JsonProperty("border-color") String borderColor,
                  @JsonProperty("border-opacity") String borderOpacity,
                  @JsonProperty("background-color") String backgroundColor,
                  @JsonProperty("shape") String shape,
                  @JsonProperty("height") String height,
                  @JsonProperty("width") String width,
                  @JsonProperty("line-color") String lineColor) {
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.borderOpacity = borderOpacity;
        this.backgroundColor = backgroundColor;
        this.shape = shape;
        this.height = height;
        this.width = width;
        this.lineColor = lineColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getBorderOpacity() {
        return borderOpacity;
    }

    public String getBorderWidth() {
        return borderWidth;
    }

    public String getShape() {
        return shape;
    }

    public String getHeight() {
        return height;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public String getWidth() {
        return width;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderOpacity(String borderOpacity) {
        this.borderOpacity = borderOpacity;
    }

    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
