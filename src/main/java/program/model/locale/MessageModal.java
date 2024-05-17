package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageModal {
    private String title;
    private String close;

    public MessageModal() {
    }

    public MessageModal(
            String title,
            String close
    ) {
        this.title = title;
        this.close = close;
    }

    public String getClose() {
        return close;
    }

    public String getTitle() {
        return title;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
