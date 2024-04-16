package program.model.metainfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {
    private int id;
    private String type;
    private String name;
    @JsonProperty("max_gen")
    private int maxGeneration;
    @JsonProperty("min_gen")
    private int minGeneration;
    private float price;
    private float cost;

    public Source () {}

    @JsonCreator
    public Source(int id,
                  String type,
                  String name,
                  @JsonProperty("max_gen") int maxGeneration,
                  @JsonProperty("min_gen") int minGeneration,
                  float price,
                  float cost){
        this.id = id;
        this.type = type;
        this.name = name;
        this.maxGeneration = maxGeneration;
        this.minGeneration = minGeneration;
        this.price = price;
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

    public float getCost() {
        return cost;
    }

    public float getPrice() {
        return price;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public int getMinGeneration() {
        return minGeneration;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setMaxGeneration(int maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    public void setMinGeneration(int minGeneration) {
        this.minGeneration = minGeneration;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
