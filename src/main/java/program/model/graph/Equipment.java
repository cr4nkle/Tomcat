package program.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {
    @JsonProperty("id")
    private int id;
    @JsonProperty("installed")
    private boolean installed;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private float price;
    @JsonProperty("throughput")
    private int throughput;
    @JsonProperty("resistance")
    private int resistance;
    @JsonProperty("cost")
    private int cost;
    @JsonProperty("max_gen")
    private int maxGen;
    @JsonProperty("min_gen")
    private int minGen;
    @JsonProperty("load")
    private int load;

    public Equipment() {

    }

    public Equipment(
            @JsonProperty("id") int id,
            @JsonProperty("installed") boolean installed,
            @JsonProperty("name") String name,
            @JsonProperty("price") float price,
            @JsonProperty("throughput") int throughput,
            @JsonProperty("resistance") int resistance,
            @JsonProperty("cost") int cost,
            @JsonProperty("max_gen") int maxGen,
            @JsonProperty("min_gen") int minGen,
            @JsonProperty("load") int load) {
        this.id = id;
        this.installed = installed;
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.throughput = throughput;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.resistance = resistance;
        this.load = load;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getThroughput() {
        return throughput;
    }

    public int getResistance() {
        return resistance;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public int getMinGen() {
        return minGen;
    }

    public void setMaxGen(int maxGen) {
        this.maxGen = maxGen;
    }

    public void setMinGen(int minGen) {
        this.minGen = minGen;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getLoad() {
        return load;
    }
}
