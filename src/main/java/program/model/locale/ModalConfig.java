package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModalConfig {
    @JsonProperty("startModal")
    private StartModal startModal;
    @JsonProperty("newModelModal")
    private NewModelModal newModelModal;
    @JsonProperty("chooseModelModal")
    private ChooseModelModal chooseModelModal;
    @JsonProperty("messageModal")
    private MessageModal messageModal;
    @JsonProperty("nodeModal")
    private NodeModal nodeModal;
    @JsonProperty("resultModal")
    private ResultModal resultModal;
    @JsonProperty("toolbarButtons")
    private ToolbarButtons toolbarButtons;

    public ModalConfig() {
    }

    public ModalConfig(
            @JsonProperty("startModal") StartModal startModal,
            @JsonProperty("newModelModal") NewModelModal newModelModal,
            @JsonProperty("chooseModelModal") ChooseModelModal chooseModelModal,
            @JsonProperty("messageModal") MessageModal messageModal,
            @JsonProperty("nodeModal") NodeModal nodeModal,
            @JsonProperty("resultModal") ResultModal resultModal,
            @JsonProperty("toolbarButtons") ToolbarButtons toolbarButtons
    ) {
        this.startModal = startModal;
        this.newModelModal = newModelModal;
        this.chooseModelModal = chooseModelModal;
        this.messageModal = messageModal;
        this.nodeModal = nodeModal;
        this.resultModal = resultModal;
        this.toolbarButtons = toolbarButtons;
    }

    public StartModal getStartModal() {
        return startModal;
    }

    public MessageModal getMessageModal() {
        return messageModal;
    }

    public ChooseModelModal getChooseModelModal() {
        return chooseModelModal;
    }

    public NewModelModal getNewModelModal() {
        return newModelModal;
    }

    public void setChooseModelModal(ChooseModelModal chooseModelModal) {
        this.chooseModelModal = chooseModelModal;
    }

    public void setNewModelModal(NewModelModal newModelModal) {
        this.newModelModal = newModelModal;
    }

    public void setMessageModal(MessageModal messageModal) {
        this.messageModal = messageModal;
    }

    public void setStartModal(StartModal startModal) {
        this.startModal = startModal;
    }

    public NodeModal getNodeModal() {
        return nodeModal;
    }

    public ResultModal getResultModal() {
        return resultModal;
    }

    public void setNodeModal(NodeModal nodeModal) {
        this.nodeModal = nodeModal;
    }

    public void setResultModal(ResultModal resultModal) {
        this.resultModal = resultModal;
    }

    public ToolbarButtons getToolbarButtons() {
        return toolbarButtons;
    }

    public void setToolbarButtons(ToolbarButtons toolbarButtons) {
        this.toolbarButtons = toolbarButtons;
    }
}

