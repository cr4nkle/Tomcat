package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Elements {
    private String sourceToolbarBtn;
    private String distributionNodeToolbarBtn;
    private String consumerToolbarBtn;

    public Elements() {
    }

    public Elements(
            String sourceToolbarBtn,
            String distributionNodeToolbarBtn,
            String consumerToolbarBtn
    ) {
        this.sourceToolbarBtn = sourceToolbarBtn;
        this.distributionNodeToolbarBtn = distributionNodeToolbarBtn;
        this.consumerToolbarBtn = consumerToolbarBtn;
    }

    public String getSourceToolbarBtn() {
        return sourceToolbarBtn;
    }

    public String getDistributionNodeToolbarBtn() {
        return distributionNodeToolbarBtn;
    }

    public String getConsumerToolbarBtn() {
        return consumerToolbarBtn;
    }

    public void setSourceToolbarBtn(String sourceToolbarBtn) {
        this.sourceToolbarBtn = sourceToolbarBtn;
    }

    public void setDistributionNodeToolbarBtn(String distributionNodeToolbarBtn) {
        this.distributionNodeToolbarBtn = distributionNodeToolbarBtn;
    }

    public void setConsumerToolbarBtn(String consumerToolbarBtn) {
        this.consumerToolbarBtn = consumerToolbarBtn;
    }
}
