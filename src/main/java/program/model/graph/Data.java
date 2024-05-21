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
    @JsonProperty("node_type")
    private String nodeType;
    @JsonProperty("source")
    private String source;
    @JsonProperty("target")
    private String target;
    @JsonProperty("system_type")
    private String systemType;
    @JsonProperty("length")
    private int length;
    @JsonProperty("equipment")
    private ArrayList<Equipment> equipment;
    @JsonProperty("grouped")
    private String grouped;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("name")
    private String name;

    public Data() {
    }

    @JsonCreator
    public Data(@JsonProperty("id") String id,
                @JsonProperty("node_type") String nodeType,
                @JsonProperty("source") String source,
                @JsonProperty("target") String target,
                @JsonProperty("system_type") String systemType,
                @JsonProperty("length") int length,
                @JsonProperty("equipment") ArrayList<Equipment> equipment,
                @JsonProperty("group_name") String groupName,
                @JsonProperty("grouped") String grouped,
                @JsonProperty("name") String name) {
        this.id = id;
        this.nodeType = nodeType;
        this.source = source;
        this.target = target;
        this.systemType = systemType;
        this.length = length;
        this.equipment = equipment;
        this.grouped = grouped;
        this.groupName = groupName;
        this.name = name;
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

    public String getGrouped() {
        return grouped;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
