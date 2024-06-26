package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operations {
    private String addEdgeToolbarBtn;
    private String addNodeToolbarBtn;
    private String editToolbarBtn;
    private String deleteItemToolbarBtn;
    private String calculateToolbarBtn;
    private String moveToolbarBtn;
    private String bullseyeToolbarBtn;

    public Operations() {
    }

    public Operations(
            String addEdgeToolbarBtn,
            String addNodeToolbarBtn,
            String editToolbarBtn,
            String deleteItemToolbarBtn,
            String calculateToolbarBtn,
            String moveToolbarBtn,
            String bullseyeToolbarBtn
    ) {
        this.addEdgeToolbarBtn = addEdgeToolbarBtn;
        this.addNodeToolbarBtn = addNodeToolbarBtn;
        this.editToolbarBtn = editToolbarBtn;
        this.deleteItemToolbarBtn = deleteItemToolbarBtn;
        this.calculateToolbarBtn = calculateToolbarBtn;
        this.moveToolbarBtn = moveToolbarBtn;
        this.bullseyeToolbarBtn = bullseyeToolbarBtn;
    }

    public String getAddEdgeToolbarBtn() {
        return addEdgeToolbarBtn;
    }

    public String getAddNodeToolbarBtn() {
        return addNodeToolbarBtn;
    }

    public String getEditToolbarBtn() {
        return editToolbarBtn;
    }

    public String getDeleteItemToolbarBtn() {
        return deleteItemToolbarBtn;
    }

    public String getCalculateToolbarBtn() {
        return calculateToolbarBtn;
    }

    public String getMoveToolbarBtn() {
        return moveToolbarBtn;
    }

    public String getBullseyeToolbarBtn(){
        return bullseyeToolbarBtn;
    }

    public void setAddEdgeToolbarBtn(String addEdgeToolbarBtn) {
        this.addEdgeToolbarBtn = addEdgeToolbarBtn;
    }

    public void setAddNodeToolbarBtn(String addNodeToolbarBtn) {
        this.addNodeToolbarBtn = addNodeToolbarBtn;
    }

    public void setEditToolbarBtn(String editToolbarBtn) {
        this.editToolbarBtn = editToolbarBtn;
    }

    public void setDeleteItemToolbarBtn(String deleteItemToolbarBtn) {
        this.deleteItemToolbarBtn = deleteItemToolbarBtn;
    }

    public void setCalculateToolbarBtn(String calculateToolbarBtn) {
        this.calculateToolbarBtn = calculateToolbarBtn;
    }

    public void setMoveToolbarBtn(String moveToolbarBtn) {
        this.moveToolbarBtn = moveToolbarBtn;
    }

    public void setBullseyeToolbarBtn(String bullseyeToolbarBtn){
        this.bullseyeToolbarBtn = bullseyeToolbarBtn;
    }
}
