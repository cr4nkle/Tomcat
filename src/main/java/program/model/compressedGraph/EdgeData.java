package program.model.compressedGraph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdgeData {
    @JsonProperty("system_type")
    private String systemType;
    @JsonProperty("throughput")
    private int throughput;
    @JsonProperty("resistance")
    private int resistance;
    @JsonProperty("cost")
    private int cost;
    @JsonProperty("length")
    private double length;
    @JsonProperty("source")
    private String source;
    @JsonProperty("target")
    private String target;

    public EdgeData() {

    }

    @JsonCreator
    public EdgeData(
            @JsonProperty("system_type") String systemType,
            @JsonProperty("throughput") int throughput,
            @JsonProperty("resistance") int resistance,
            @JsonProperty("cost") int cost,
            @JsonProperty("length") double length,
            @JsonProperty("source") String source,
            @JsonProperty("target") String target) {
        this.cost = cost;
        this.systemType = systemType;
        this.length = length;
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

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "systemType='" + systemType + '\'' +
                ", throughput=" + throughput +
                ", resistance=" + resistance +
                ", cost=" + cost +
                ", length=" + length +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
