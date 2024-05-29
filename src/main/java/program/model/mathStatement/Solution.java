package program.model.mathStatement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Solution {
    @JsonProperty("statement")
    private MathStatement mathStatement;
    @JsonProperty("objective")
    private double objective;
    @JsonProperty("variables")
    private double[] variables;

    public Solution() {

    }

    @JsonCreator
    public Solution(
            @JsonProperty("statement") MathStatement mathStatement,
            @JsonProperty("objective") double objective,
            @JsonProperty("variables") double[] variables) {
        this.mathStatement = mathStatement;
        this.objective = objective;
        this.variables = variables;
    }

    public double getObjective() {
        return objective;
    }

    public double[] getVariables() {
        return variables;
    }

    public MathStatement getMathStatement() {
        return mathStatement;
    }

    public void setMathStatement(MathStatement mathStatement) {
        this.mathStatement = mathStatement;
    }

    public void setObjective(double objective) {
        this.objective = objective;
    }

    public void setVariables(double[] variables) {
        this.variables = variables;
    }
}
