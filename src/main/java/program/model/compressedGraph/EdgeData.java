package program.model.compressedGraph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdgeData {
    @JsonProperty("system-type")
    private String systemType;
    @JsonProperty("throughput")
    private int throughput;
    @JsonProperty("resistance")
    private int resistance;
    @JsonProperty("cost")
    private int cost;
    @JsonProperty("max-generation")
    private int maxGen;
    @JsonProperty("min-generation")
    private int minGen;
    @JsonProperty("source")
    private String source;
    @JsonProperty("target")
    private String target;

    public EdgeData() {

    }

    @JsonCreator
    public EdgeData(
            @JsonProperty("system-type") String systemType,
            @JsonProperty("throughput") int throughput,
            @JsonProperty("resistance") int resistance,
            @JsonProperty("cost") int cost,
            @JsonProperty("max-generation") int maxGen,
            @JsonProperty("min-generation") int minGen,
            @JsonProperty("source") String source,
            @JsonProperty("target") String target) {
        this.cost = cost;
        this.systemType = systemType;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.source = source;
        this.throughput = throughput;
        this.resistance = resistance;
        this.target = target;
    }

    public int getCost() {
        return cost;
    }

    public int getResistance() {
        return resistance;
    }

    public int getThroughput() {
        return throughput;
    }

    public String getTarget() {
        return target;
    }

    public String getSystemType() {
        return systemType;
    }

    public String getSource() {
        return source;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public int getMinGen() {
        return minGen;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setMaxGen(int maxGen) {
        this.maxGen = maxGen;
    }

    public void setMinGen(int minGen) {
        this.minGen = minGen;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }
}
