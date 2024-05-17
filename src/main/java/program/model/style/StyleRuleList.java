package program.model.style;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StyleRuleList {
    @JsonProperty("style")
    private List<StyleRule> styleRuleList;

    public StyleRuleList() {}

    public StyleRuleList(
            @JsonProperty("style") List<StyleRule> styleRuleList
    ){
        this.styleRuleList = styleRuleList;
    }

    public List<StyleRule> getStyleRuleList() {
        return styleRuleList;
    }

    public void setStyleRuleList(List<StyleRule> styleRuleList) {
        this.styleRuleList = styleRuleList;
    }
}
