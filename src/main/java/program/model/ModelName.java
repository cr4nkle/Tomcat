package program.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

@JsonRootName("models")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelName {
    @JsonProperty("name")
    private ArrayList<String> modelName;

    public ModelName () {
        this.modelName = new ArrayList<>();
    }

    @JsonCreator
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
