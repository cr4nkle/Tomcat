package program.model.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {
    @JsonProperty("data")
    private Data data;
    @JsonProperty("position")
    private Position position;
    @JsonProperty("selected")
    private boolean selected;
    @JsonProperty("locked")
    private boolean locked;

    public Node() {
    }

    @JsonCreator
    public Node(
            @JsonProperty("data") Data data,
            @JsonProperty("position") Position position,
            @JsonProperty("selected") boolean selected,
            @JsonProperty("locked") boolean locked) {
        this.data = data;
        this.position = position;
        this.selected = selected;
        this.locked = locked;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
