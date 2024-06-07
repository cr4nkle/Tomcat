package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultModal {
    private String title;
    private String save;
    private String close;
    private String calculate;
    private ResultCheckboxes checkboxes;

    public ResultModal() {
    }

    public ResultModal(
            String title,
            String save,
            String close,
            String calculate,
            ResultCheckboxes checkboxes) {
        this.title = title;
        this.save = save;
        this.close = close;
        this.calculate = calculate;
        this.checkboxes = checkboxes;
    }

    public String getTitle() {
        return title;
    }

    public String getSave() {
        return save;
    }

    public String getClose() {
        return close;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void steCalculate(String calculate) {
        this.calculate = calculate;
    }

    public String getCalculate() {
        return calculate;
    }

    public void setCheckboxes(ResultCheckboxes checkboxes) {
        this.checkboxes = checkboxes;
    }

    public ResultCheckboxes getCheckboxes() {
        return checkboxes;
    }
}
