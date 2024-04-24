package program.model.mathStatement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MathStatement {
    @JsonProperty("b")
    private ArrayList<Integer> b;
    @JsonProperty("max")
    private ArrayList<Integer> max;
    @JsonProperty("min")
    private ArrayList<Integer> min;
    @JsonProperty("goal")
    private ArrayList<Integer> goal;
    @JsonProperty("matrix")
    private ArrayList<ArrayList<Integer>> matrix;

    public MathStatement () {}

    @JsonCreator
    public MathStatement (@JsonProperty("b") ArrayList<Integer> b,
                          @JsonProperty("max") ArrayList<Integer> max,
                          @JsonProperty("min") ArrayList<Integer> min,
                          @JsonProperty("goal") ArrayList<Integer> goal,
                          @JsonProperty("matrix") ArrayList<ArrayList<Integer>> matrix){
        this.b = b;
        this.min = min;
        this.max = max;
        this.goal = goal;
        this.matrix = matrix;
    }

    public ArrayList<Integer> getB() {
        return b;
    }

    public ArrayList<ArrayList<Integer>> getMatrix() {
        return matrix;
    }

    public ArrayList<Integer> getGoal() {
        return goal;
    }

    public ArrayList<Integer> getMax() {
        return max;
    }

    public void setB(ArrayList<Integer> b) {
        this.b = b;
    }

    public ArrayList<Integer> getMin() {
        return min;
    }

    public void setGoal(ArrayList<Integer> goal) {
        this.goal = goal;
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = matrix;
    }

    public void setMax(ArrayList<Integer> max) {
        this.max = max;
    }

    public void setMin(ArrayList<Integer> min) {
        this.min = min;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
