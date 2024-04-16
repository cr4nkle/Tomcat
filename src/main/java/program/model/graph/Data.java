package program.model.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("id")
    private String id;
    @JsonProperty("nodetype")
    private String nodeType;
    @JsonProperty("source")
    private String source;
    @JsonProperty("target")
    private String target;
    @JsonProperty("systemtype")
    private String systemType;
    @JsonProperty("length")
    private int length;
    @JsonProperty("equipment")
    private ArrayList<Equipment> equipment;

    public Data () {}

    @JsonCreator
    public Data (@JsonProperty("id") String id,
                 @JsonProperty("nodetype") String nodeType,
                 @JsonProperty("source") String source,
                 @JsonProperty("target") String target,
                 @JsonProperty("systemtype") String systemType,
                 @JsonProperty("length") int length,
                 @JsonProperty("equipment") ArrayList<Equipment> equipment) {
        this.id = id;
        this.nodeType = nodeType;
        this.source = source;
        this.target = target;
        this.systemType = systemType;
        this.length = length;
        this.equipment = equipment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public String getSource() {
        return source;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
