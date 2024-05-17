package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolbarButtons {
    private String openNewModelModalToolbarBtn;
    private String uploadToolbarBtn;
    private String openChooseModelModalToolbarBtn;
    private String saveToDataWarehouseToolbarBtn;
    private String saveToDeviceToolbarBtn;
    private String deleteModelToolbarBtn;
    private Operations operations;
    private Elements elements;

    public ToolbarButtons() {
    }

    public ToolbarButtons(
            String openNewModelModalToolbarBtn,
            String uploadToolbarBtn,
            String openChooseModelModalToolbarBtn,
            String saveToDataWarehouseToolbarBtn,
            String saveToDeviceToolbarBtn,
            String deleteModelToolbarBtn,
            Operations operations,
            Elements elements
    ) {
        this.openNewModelModalToolbarBtn = openNewModelModalToolbarBtn;
        this.uploadToolbarBtn = uploadToolbarBtn;
        this.openChooseModelModalToolbarBtn = openChooseModelModalToolbarBtn;
        this.saveToDataWarehouseToolbarBtn = saveToDataWarehouseToolbarBtn;
        this.saveToDeviceToolbarBtn = saveToDeviceToolbarBtn;
        this.deleteModelToolbarBtn = deleteModelToolbarBtn;
        this.operations = operations;
        this.elements = elements;
    }

    public String getOpenNewModelModalToolbarBtn() {
        return openNewModelModalToolbarBtn;
    }

    public String getUploadToolbarBtn() {
        return uploadToolbarBtn;
    }

    public String getOpenChooseModelModalToolbarBtn() {
        return openChooseModelModalToolbarBtn;
    }

    public String getSaveToDataWarehouseToolbarBtn() {
        return saveToDataWarehouseToolbarBtn;
    }

    public String getSaveToDeviceToolbarBtn() {
        return saveToDeviceToolbarBtn;
    }

    public String getDeleteModelToolbarBtn() {
        return deleteModelToolbarBtn;
    }

    public Operations getOperations() {
        return operations;
    }

    public Elements getElements() {
        return elements;
    }

    public void setOpenNewModelModalToolbarBtn(String openNewModelModalToolbarBtn) {
        this.openNewModelModalToolbarBtn = openNewModelModalToolbarBtn;
    }

    public void setUploadToolbarBtn(String uploadToolbarBtn) {
        this.uploadToolbarBtn = uploadToolbarBtn;
    }

    public void setOpenChooseModelModalToolbarBtn(String openChooseModelModalToolbarBtn) {
        this.openChooseModelModalToolbarBtn = openChooseModelModalToolbarBtn;
    }

    public void setSaveToDataWarehouseToolbarBtn(String saveToDataWarehouseToolbarBtn) {
        this.saveToDataWarehouseToolbarBtn = saveToDataWarehouseToolbarBtn;
    }

    public void setSaveToDeviceToolbarBtn(String saveToDeviceToolbarBtn) {
        this.saveToDeviceToolbarBtn = saveToDeviceToolbarBtn;
    }

    public void setDeleteModelToolbarBtn(String deleteModelToolbarBtn) {
        this.deleteModelToolbarBtn = deleteModelToolbarBtn;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }
}
