package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultCheckboxes {
    private String linear;
    private String nonlinear;

    public ResultCheckboxes() {
    }

    public ResultCheckboxes(
            String linear,
            String nonlinear) {
        this.linear = linear;
        this.nonlinear = nonlinear;
    }

    public void setLinear(String linear) {
        this.linear = linear;
    }

    public void setNonlinear(String nonlinear) {
        this.nonlinear = nonlinear;
    }

    public String getLinear() {
        return linear;
    }

    public String getNonlinear() {
        return nonlinear;
    }
}
