package program.model.locale;

public class ResultModal {
    private String title;
    private String save;
    private String close;

    public ResultModal() {
    }

    public ResultModal(
            String title,
            String save,
            String close
    ) {
        this.title = title;
        this.save = save;
        this.close = close;
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
}
