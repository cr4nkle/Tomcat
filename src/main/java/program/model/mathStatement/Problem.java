package program.model.mathStatement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import program.model.compressedGraph.EdgeData;
import program.model.compressedGraph.NodeData;

import java.util.ArrayList;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {
    @JsonProperty("node-id")
    private ArrayList<String> nodeId;
    @JsonProperty("edge-id")
    private ArrayList<String> edgeId;
    @JsonProperty("node")
    private HashMap<String, NodeData> node;
    @JsonProperty("edge")
    private HashMap<String, EdgeData> edge;

    public Problem() {

    }

    @JsonCreator
    public Problem(
            @JsonProperty("node-id") ArrayList<String> nodeId,
            @JsonProperty("edge-id") ArrayList<String> edgeId,
            @JsonProperty("node") HashMap<String, NodeData> node,
            @JsonProperty("edge") HashMap<String, EdgeData> edge) {
        this.edge = edge;
        this.node = node;
        this.edgeId = edgeId;
        this.nodeId = nodeId;
    }

    public HashMap<String, EdgeData> getEdge() {
        return edge;
    }

    public HashMap<String, NodeData> getNode() {
        return node;
    }

    public ArrayList<String> getEdgeId() {
        return edgeId;
    }

    public ArrayList<String> getNodeId() {
        return nodeId;
    }

    public void setEdge(HashMap<String, EdgeData> edge) {
        this.edge = edge;
    }

    public void setEdgeId(ArrayList<String> edgeId) {
        this.edgeId = edgeId;
    }

    public void setNode(HashMap<String, NodeData> node) {
        this.node = node;
    }

    public void setNodeId(ArrayList<String> nodeId) {
        this.nodeId = nodeId;
    }
}
