package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChooseModelModal {
    private String title;
    private String button;
    private String close;

    public ChooseModelModal() {
    }

    public ChooseModelModal(
            String title,
            String button,
            String close) {
        this.title = title;
        this.button = button;
        this.close = close;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getClose() {
        return close;
    }
}
