package program.model.locale;

public class ChooseModelModal {
    private String title;
    private String button;

    public ChooseModelModal() {
    }

    public ChooseModelModal(
            String title,
            String button
    ) {
        this.title = title;
        this.button = button;
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
}
