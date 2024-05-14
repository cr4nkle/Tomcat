package program.model.locale;

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
