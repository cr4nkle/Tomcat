package program.model.mathStatement;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MathStatement {
    @JsonProperty("limits")
    private ArrayList<Double> lim;
    @JsonProperty("max")
    private ArrayList<Double> max;
    @JsonProperty("min")
    private ArrayList<Double> min;
    @JsonProperty("goal")
    private ArrayList<Double> goal;
    @JsonProperty("matrix")
    private ArrayList<ArrayList<Double>> matrix;
    @JsonProperty("sign")
    private ArrayList<Integer> sign;
    @JsonProperty("type")
    private ArrayList<Boolean> type;
    @JsonIgnore
    private int count;

    public MathStatement() {
    }

    @JsonCreator
    public MathStatement(
            @JsonProperty("limits") ArrayList<Double> lim,
            @JsonProperty("max") ArrayList<Double> max,
            @JsonProperty("min") ArrayList<Double> min,
            @JsonProperty("goal") ArrayList<Double> goal,
            @JsonProperty("matrix") ArrayList<ArrayList<Double>> matrix,
            @JsonProperty("sign") ArrayList<Integer> sign,
            @JsonProperty("type") ArrayList<Boolean> type) {
        this.lim = lim;
        this.min = min;
        this.max = max;
        this.goal = goal;
        this.matrix = matrix;
        this.sign = sign;
        this.type= type;
    }

    public MathStatement(
            ArrayList<Double> lim,
            ArrayList<Double> max,
            ArrayList<Double> min,
            ArrayList<Double> goal,
            ArrayList<ArrayList<Double>> matrix,
            ArrayList<Integer> sign,
            ArrayList<Boolean> type,
            int count) {
        this.lim = lim;
        this.min = min;
        this.max = max;
        this.goal = goal;
        this.matrix = matrix;
        this.sign = sign;
        this.type= type;
        this.count = count;
    }

    public void setMin(ArrayList<Double> min) {
        this.min = min;
    }

    public void setMax(ArrayList<Double> max) {
        this.max = max;
    }

    public void setMatrix(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;
    }

    public void setGoal(ArrayList<Double> goal) {
        this.goal = goal;
    }

    public void setLim(ArrayList<Double> lim) {
        this.lim = lim;
    }

    public void setType(ArrayList<Boolean> type) {
        this.type = type;
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }

    public ArrayList<Boolean> getType() {
        return type;
    }

    public ArrayList<Double> getGoal() {
        return goal;
    }

    public ArrayList<Double> getMax() {
        return max;
    }

    public ArrayList<Double> getMin() {
        return min;
    }

    public ArrayList<Integer> getSign() {
        return sign;
    }

    public void setSign(ArrayList<Integer> sign) {
        this.sign = sign;
    }

    public ArrayList<Double> getLim() {
        return lim;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MathStatement{");
        sb.append("limits=").append(lim).append(", ");
        sb.append("max=").append(max).append(", ");
        sb.append("min=").append(min).append(", ");
        sb.append("goal=").append(goal).append(", ");
        sb.append("matrix=").append(matrix).append(", ");
        sb.append("sign=").append(sign).append(", ");
        sb.append("type=").append(type);
        sb.append("}");
        return sb.toString();
    }

}
