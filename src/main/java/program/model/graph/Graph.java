package program.model.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph {
//    @JsonProperty("name")
//    private String name;
    @JsonProperty("elements")
    private Element element;
    @JsonProperty("style")
    private ArrayList<StyleRule> styleRules;
    @JsonProperty("boxSelectionEnabled")
    private boolean boxSelectionEnabled;
    @JsonProperty("userPanningEnabled")
    private boolean userPanningEnabled;
    @JsonProperty("panningEnabled")
    private boolean panningEnabled;
    @JsonProperty("maxZoom")
    private double maxZoom;
    @JsonProperty("minZoom")
    private double minZoom;
    @JsonProperty("zoom")
    private double zoom;
    @JsonProperty("userZoomingEnabled")
    private boolean userZoomingEnabled;
    @JsonProperty("zoomingEnabled")
    private boolean zoomingEnabled;
    @JsonProperty("data")
    private Data data;
    @JsonProperty("renderer")
    private Renderer renderer;
    @JsonProperty("pan")
    private Position position;

    public Graph () {}

    @JsonCreator
    public Graph (//@JsonProperty("name") String name,
            @JsonProperty("elements") Element element,
            @JsonProperty("style") ArrayList<StyleRule> styleRules,
            @JsonProperty("boxSelectionEnabled") boolean boxSelectionEnabled,
            @JsonProperty("userPanningEnabled") boolean userPanningEnabled,
            @JsonProperty("panningEnabled") boolean panningEnabled,
            @JsonProperty("maxZoom") double maxZoom,
            @JsonProperty("minZoom") double minZoom,
            @JsonProperty("zoom") double zoom,
            @JsonProperty("userZoomingEnabled") boolean userZoomingEnabled,
            @JsonProperty("zoomingEnabled") boolean zoomingEnabled,
            @JsonProperty("data") Data data,
            @JsonProperty("renderer") Renderer renderer,
            @JsonProperty("pan") Position position) {
//        this.name = name;
        this.element = element;
        this.styleRules = styleRules;
        this.maxZoom = maxZoom;
        this.boxSelectionEnabled = boxSelectionEnabled;
        this.userZoomingEnabled = userZoomingEnabled;
        this.zoomingEnabled = zoomingEnabled;
        this.zoom = zoom;
        this.userPanningEnabled = userPanningEnabled;
        this.panningEnabled = panningEnabled;
        this.minZoom = minZoom;
        this.data = data;
        this.renderer = renderer;
        this.position = position;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isBoxSelectionEnabled() {
        return boxSelectionEnabled;
    }

    public boolean isPanningEnabled() {
        return panningEnabled;
    }

    public boolean isUserPanningEnabled() {
        return userPanningEnabled;
    }

    public boolean isUserZoomingEnabled() {
        return userZoomingEnabled;
    }

    public boolean isZoomingEnabled() {
        return zoomingEnabled;
    }

    public double getMaxZoom() {
        return maxZoom;
    }

    public double getMinZoom() {
        return minZoom;
    }

    public double getZoom() {
        return zoom;
    }

    public void setBoxSelectionEnabled(boolean boxSelectionEnabled) {
        this.boxSelectionEnabled = boxSelectionEnabled;
    }

    public void setMaxZoom(double maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setMinZoom(double minZoom) {
        this.minZoom = minZoom;
    }

    public void setPanningEnabled(boolean panningEnabled) {
        this.panningEnabled = panningEnabled;
    }

    public void setUserPanningEnabled(boolean userPanningEnabled) {
        this.userPanningEnabled = userPanningEnabled;
    }

    public void setUserZoomingEnabled(boolean userZoomingEnabled) {
        this.userZoomingEnabled = userZoomingEnabled;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void setZoomingEnabled(boolean zoomingEnabled) {
        this.zoomingEnabled = zoomingEnabled;
    }

    public Position getPosition() {
        return position;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public ArrayList<StyleRule> getStyleRules() {
        return styleRules;
    }

    public void setStyleRules(ArrayList<StyleRule> styleRules) {
        this.styleRules = styleRules;
    }

    @Override
    public String toString() {
        return "elements=" + element.toString();
    }
}
