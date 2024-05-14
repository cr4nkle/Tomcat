package program.model.locale;

public class StartModal {
    private String title;
    private Buttons buttons;

    public StartModal() {
    }

    public StartModal(
            String title,
            Buttons buttons
    ) {
        this.title = title;
        this.buttons = buttons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Buttons getButtons() {
        return buttons;
    }

    public void setButtons(Buttons buttons) {
        this.buttons = buttons;
    }
}
