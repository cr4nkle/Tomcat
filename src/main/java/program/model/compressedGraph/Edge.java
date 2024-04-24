package program.model.compressedGraph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import program.model.graph.Position;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edge {
    private Data data;
    private Position position;
    private boolean selected;

    public Edge() {
    }

    @JsonCreator
    public Edge(@JsonProperty("data") Data data,
                @JsonProperty("position") Position position,
                @JsonProperty("selected") boolean selected) {
        this.data = data;
        this.position = position;
        this.selected = selected;
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
}
