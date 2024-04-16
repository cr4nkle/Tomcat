package program.model.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edge {
    @JsonProperty("data")
    private Data data;
    @JsonProperty("position")
    private Position position;
    @JsonProperty("group")
    private String group;
    @JsonProperty("removed")
    private boolean removed;
    @JsonProperty("selected")
    private boolean selected;
    @JsonProperty("selectable")
    private boolean selectable;
    @JsonProperty("locked")
    private boolean locked;
    @JsonProperty("grabbable")
    private boolean grabbable;
    @JsonProperty("pannable")
    private boolean pannable;
    @JsonProperty("classes")
    private String classes;

    public Edge () {}

    @JsonCreator
    public Edge (@JsonProperty("data") Data data,
                 @JsonProperty("position") Position position,
                 @JsonProperty("group") String group,
                 @JsonProperty("removed") boolean removed,
                 @JsonProperty("selected") boolean selected,
                 @JsonProperty("selectable") boolean selectable,
                 @JsonProperty("locked") boolean locked,
                 @JsonProperty("grabbable") boolean grabbable,
                 @JsonProperty("pannable") boolean pannable,
                 @JsonProperty("classes") String classes) {
        this.data = data;
        this.classes = classes;
        this.position = position;
        this.grabbable = grabbable;
        this.selectable = selectable;
        this.selected = selected;
        this.locked = locked;
        this.pannable = pannable;
        this.group = group;
        this.removed = removed;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public boolean isGrabbable() {
        return grabbable;
    }

    public boolean isRemoved() {
        return removed;
    }

    public boolean isLocked() {
        return locked;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isPannable() {
        return pannable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setGrabbable(boolean grabbable) {
        this.grabbable = grabbable;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setPannable(boolean pannable) {
        this.pannable = pannable;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

