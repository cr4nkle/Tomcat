package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeModal {
    private String title;
    private Checkboxes checkboxes;
    private String save;
    private String close;
    private String elementPlaceholder;
    private String groupPlaceholder;

    public NodeModal() {
    }

    public NodeModal(
            String title,
            Checkboxes checkboxes,
            String save,
            String close,
            String elementPlaceholder,
            String groupPlaceholder) {
        this.title = title;
        this.checkboxes = checkboxes;
        this.save = save;
        this.close = close;
        this.elementPlaceholder = elementPlaceholder;
        this.groupPlaceholder = groupPlaceholder;
    }

    public String getTitle() {
        return title;
    }

    public Checkboxes getCheckboxes() {
        return checkboxes;
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

    public void setCheckboxes(Checkboxes checkboxes) {
        this.checkboxes = checkboxes;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setElementPlaceholder(String elementPlaceholder) {
        this.elementPlaceholder = elementPlaceholder;
    }

    public void setGroupPlaceholder(String groupPlaceholder) {
        this.groupPlaceholder = groupPlaceholder;
    }

    public String getElementPlaceholder() {
        return elementPlaceholder;
    }

    public String getgroupPlaceholder() {
        return groupPlaceholder;
    }
}
