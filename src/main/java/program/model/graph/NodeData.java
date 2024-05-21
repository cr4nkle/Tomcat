package program.model.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeData {
    @JsonProperty("system_type")
    private String systemType;
    @JsonProperty("node_type")
    private String nodeType;
    @JsonProperty("price")
    private float price;
    @JsonProperty("cost")
    private int cost;
    @JsonProperty("max_generation")
    private int maxGen;
    @JsonProperty("min_generation")
    private int minGen;
    @JsonProperty("load")
    private int load;
    @JsonProperty("installed")
    private boolean installed;
    @JsonProperty("efficiency")
    private float efficiency;

    public NodeData() {

    }

    @JsonCreator
    public NodeData(
            @JsonProperty("node_type") String nodeType,
            @JsonProperty("price") int price,
            @JsonProperty("cost") int cost,
            @JsonProperty("max_generation") int maxGen,
            @JsonProperty("min_generation") int minGen,
            @JsonProperty("load") int load,
            @JsonProperty("system_type") String systemType,
            @JsonProperty("installed") boolean installed,
            @JsonProperty("efficiency") float efficiency) {
        this.cost = cost;
        this.load = load;
        this.nodeType = nodeType;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.price = price;
        this.systemType = systemType;
        this.installed = installed;
        this.efficiency = efficiency;
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

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "systemType='" + systemType + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", maxGen=" + maxGen +
                ", minGen=" + minGen +
                ", load=" + load +
                ", installed=" + installed +
                '}';
    }
}
