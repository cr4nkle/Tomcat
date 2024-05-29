package program.model.mathStatement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NonLinearSolution {
    private String data;
    private String model;
    private String run;
    @JsonProperty("statement")
    private MathStatement mathStatement;
    @JsonProperty("objective")
    private final double objective = 0.0;
    @JsonProperty("variables")
    private final double[] variables = { 0.0 };

    public NonLinearSolution() {

    }

    @JsonCreator
    public NonLinearSolution(
            String data,
            String model,
            String run,
            @JsonProperty("statement") MathStatement mathStatement) {
        this.data = data;
        this.model = model;
        this.run = run;
        this.mathStatement = mathStatement;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getData() {
        return data;
    }

    public String getModel() {
        return model;
    }

    public String getRun() {
        return run;
    }

    public void setMathStatement(MathStatement mathStatement) {
        this.mathStatement = mathStatement;
    }

    public MathStatement getMathStatement() {
        return mathStatement;
    }
}
