package program.model.mathStatement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import program.model.graph.EdgeData;
import program.model.graph.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {
    @JsonProperty("node_id")
    private ArrayList<String> nodeId;
    @JsonProperty("edge_id")
    private ArrayList<String> edgeId;
    @JsonProperty("node")
    private HashMap<String, NodeData> node;
    @JsonProperty("edge")
    private HashMap<String, EdgeData> edge;

    public Problem() {

    }

    @JsonCreator
    public Problem(
            @JsonProperty("node_id") ArrayList<String> nodeId,
            @JsonProperty("edge_id") ArrayList<String> edgeId,
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problem{");
        sb.append("nodeId=").append(nodeId).append(", ");
        sb.append("edgeId=").append(edgeId).append(", ");
        sb.append("node={");
        for (Map.Entry<String, NodeData> entry : node.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("}, ");
        sb.append("edge={");
        for (Map.Entry<String, EdgeData> entry : edge.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("}}");
        return sb.toString();
    }
}
