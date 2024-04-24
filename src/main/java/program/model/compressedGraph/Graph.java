package program.model.compressedGraph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph {
    @JsonProperty("name")
    private String name;
    @JsonProperty("elements")
    private Element element;

    public Graph() {

    }

    @JsonCreator
    public Graph(
            @JsonProperty("name") String name,
            @JsonProperty("elements") Element element) {
        this.name = name;
        this.element = element;
    }

    public Element getElements() {
        return element;
    }

    public void setElements(Element element) {
        this.element = element;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
