package program.model.metainfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Line {
    private int id;
    private String type;
    private String name;
    private int throughput;
    private int resistance;
    private float cost;

    public Line () {}

    public Line(int id, String type, String name, int throughput, int resistance, float cost){
        this.id = id;
        this.type = type;
        this.name = name;
        this.throughput = throughput;
        this.resistance = resistance;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public int getResistance() {
        return resistance;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }
}
