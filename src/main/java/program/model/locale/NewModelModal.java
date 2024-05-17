package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewModelModal {
    private String title;
    private String placeholder;
    private String createModel;

    public NewModelModal() {
    }

    public NewModelModal(
            String title,
            String placeholder,
            String createModel
    ) {
        this.title = title;
        this.placeholder = placeholder;
        this.createModel = createModel;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getCreateModel() {
        return createModel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setCreateModel(String createModel) {
        this.createModel = createModel;
    }
}
