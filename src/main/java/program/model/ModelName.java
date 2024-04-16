package program.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

@JsonRootName("models")
public class ModelName {
    @JsonProperty("name")
    private ArrayList<String> modelName;

    public ModelName () {
        this.modelName = new ArrayList<>();
    }

    public ModelName (@JsonProperty("name") ArrayList<String> modelName) {
        this.modelName = modelName;
    }

    public void setModelName(ArrayList<String> modelName) {
        this.modelName = modelName;
    }

    public ArrayList<String> getModelName() {
        return modelName;
    }

    public void addModelName(String modelName) {
        this.modelName.add(modelName);
    }
}
