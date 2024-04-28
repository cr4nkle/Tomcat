package program.model.compressedGraph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeData {
    @JsonProperty("system-type")
    private String systemType;
    @JsonProperty("node-type")
    private String nodeType;
    @JsonProperty("price")
    private float price;
    @JsonProperty("cost")
    private int cost;
    @JsonProperty("max-generation")
    private int maxGen;
    @JsonProperty("min-generation")
    private int minGen;
    @JsonProperty("load")
    private int load;
    @JsonProperty("installed")
    private boolean installed;

    public NodeData() {

    }

    @JsonCreator
    public NodeData(
            @JsonProperty("node-type") String nodeType,
            @JsonProperty("price") int price,
            @JsonProperty("cost") int cost,
            @JsonProperty("max-generation") int maxGen,
            @JsonProperty("min-generation") int minGen,
            @JsonProperty("load") int load,
            @JsonProperty("system-type") String systemType,
            @JsonProperty("installed") boolean installed) {
        this.cost = cost;
        this.load = load;
        this.nodeType = nodeType;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.price = price;
        this.systemType = systemType;
        this.installed = installed;
    }

    public int getCost() {
        return cost;
    }

    public String getNodeType() {
        return nodeType;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public int getMinGen() {
        return minGen;
    }

    public int getLoad() {
        return load;
    }

    public String getSystemType() {
        return systemType;
    }

    public boolean isInstalled() {
        return installed;
    }

    public float getPrice() {
        return price;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public void setMinGen(int minGen) {
        this.minGen = minGen;
    }

    public void setMaxGen(int maxGen) {
        this.maxGen = maxGen;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}
