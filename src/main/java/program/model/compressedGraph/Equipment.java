package program.model.compressedGraph;

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

    public Equipment(){

    }

    public Equipment(
            @JsonProperty("id") int id,
            @JsonProperty("installed") boolean installed){
        this.id = id;
        this.installed = installed;
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
}
