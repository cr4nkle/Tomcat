package program.model.style;

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
    @JsonProperty("label")
    private String label;
    @JsonProperty("font-size")
    private String fontSize;
    @JsonProperty("text-margin-y")
    private String textMarginY;
    @JsonProperty("text-margin-x")
    private String textMarginX;
    @JsonProperty("curve-style")
    private String curveStyle;
    @JsonProperty("arrow-scale")
    private String arrowScale;
    @JsonProperty("target-arrow-shape")
    private String targetArrowShape;
    @JsonProperty("line-style")
    private String lineStyle;
    @JsonProperty("target-arrow-color")
    private String targetArrowColor;


    public Style() {
    }

    public Style(
            @JsonProperty("border-width") String borderWidth,
            @JsonProperty("border-color") String borderColor,
            @JsonProperty("border-opacity") String borderOpacity,
            @JsonProperty("background-color") String backgroundColor,
            @JsonProperty("shape") String shape,
            @JsonProperty("height") String height,
            @JsonProperty("width") String width,
            @JsonProperty("line-color") String lineColor,
            @JsonProperty("label") String label,
            @JsonProperty("font-size") String fontSize,
            @JsonProperty("text-margin-y") String textMarginY,
            @JsonProperty("text-margin-x") String textMarginX,
            @JsonProperty("curve-style") String curveStyle,
            @JsonProperty("arrow-scale") String arrowScale,
            @JsonProperty("target-arrow-shape") String targetArrowShape,
            @JsonProperty("line-style") String lineStyle,
            @JsonProperty("target-arrow-color") String targetArrowColor
    ) {
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.borderOpacity = borderOpacity;
        this.backgroundColor = backgroundColor;
        this.shape = shape;
        this.height = height;
        this.width = width;
        this.lineColor = lineColor;
        this.label = label;
        this.fontSize = fontSize;
        this.targetArrowColor = targetArrowColor;
        this.curveStyle = curveStyle;
        this.arrowScale = arrowScale;
        this.lineStyle = lineStyle;
        this.targetArrowShape = targetArrowShape;
        this.textMarginX = textMarginX;
        this.textMarginY = textMarginY;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getCurveStyle() {
        return curveStyle;
    }

    public String getArrowScale() {
        return arrowScale;
    }

    public String getLineStyle() {
        return lineStyle;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getTargetArrowColor() {
        return targetArrowColor;
    }

    public String getTargetArrowShape() {
        return targetArrowShape;
    }

    public String getTextMarginX() {
        return textMarginX;
    }

    public String getTextMarginY() {
        return textMarginY;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setCurveStyle(String curveStyle) {
        this.curveStyle = curveStyle;
    }

    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setArrowScale(String arrowScale) {
        this.arrowScale = arrowScale;
    }

    public void setTargetArrowColor(String targetArrowColor) {
        this.targetArrowColor = targetArrowColor;
    }

    public void setTargetArrowShape(String targetArrowShape) {
        this.targetArrowShape = targetArrowShape;
    }

    public void setTextMarginX(String textMarginX) {
        this.textMarginX = textMarginX;
    }

    public void setTextMarginY(String textMarginY) {
        this.textMarginY = textMarginY;
    }
}
