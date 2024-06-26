package program.model.style;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StyleRule {
    @JsonProperty("selector")
    private String selector;
    @JsonProperty("style")
    private Style style;

    public StyleRule () {}

    @JsonCreator
    public StyleRule (
            @JsonProperty("selector") String selector,
            @JsonProperty("style") Style style) {
        this.selector = selector;
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
