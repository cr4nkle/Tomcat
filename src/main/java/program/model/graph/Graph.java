package program.model.graph;

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
    @JsonProperty("node_ids")
    private String[] nodeIds;
    @JsonProperty("edge_ids")
    private String[] edgeIds;

    public Graph() {

    }

    @JsonCreator
    public Graph(
            @JsonProperty("name") String name,
            @JsonProperty("elements") Element element,
            @JsonProperty("node_ids") String[] nodeIds,
            @JsonProperty("edge_ids") String[] edgeIds) {
        this.name = name;
        this.element = element;
        this.edgeIds = edgeIds;
        this.nodeIds = nodeIds;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String[] getNodeIds() {
        return nodeIds;
    }

    public String[] getEdgeIds() {
        return edgeIds;
    }

    public void setEdgeIds(String[] edgeIds) {
        this.edgeIds = edgeIds;
    }

    public void setNodeIds(String[] nodeIds) {
        this.nodeIds = nodeIds;
    }
}
