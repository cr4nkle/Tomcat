package program.model.locale;

public class NodeModal {
    private String title;
    private Checkboxes checkboxes;
    private Inf inf;
    private String save;
    private String close;

    public NodeModal() {
    }

    public NodeModal(
            String title,
            Checkboxes checkboxes,
            Inf inf,
            String save,
            String close
    ) {
        this.title = title;
        this.checkboxes = checkboxes;
        this.inf = inf;
        this.save = save;
        this.close = close;
    }

    public String getTitle() {
        return title;
    }

    public Checkboxes getCheckboxes() {
        return checkboxes;
    }

    public Inf getInf() {
        return inf;
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

    public void setInf(Inf inf) {
        this.inf = inf;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setClose(String close) {
        this.close = close;
    }
}
