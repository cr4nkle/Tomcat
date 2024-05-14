package program.model.locale;

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
