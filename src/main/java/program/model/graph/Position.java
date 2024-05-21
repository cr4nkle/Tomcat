package program.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {
    @JsonProperty("x")
    private double x;
    @JsonProperty("y")
    private double y;

    public Position() {
    }

    public Position(
            @JsonProperty("x") double x,
            @JsonProperty("y") double y
    ) {
        this.y = y;
        this.x = x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}
