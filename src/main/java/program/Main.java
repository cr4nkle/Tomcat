package program;

import com.fasterxml.jackson.databind.SerializationFeature;
import program.logic.neos.NeosClient;
import program.logic.solver.LinearSolver;
import program.logic.statement.LinearStatement;
import program.logic.statement.NonLinearStatement;
import program.logic.xml.XmlGenerator;
import program.model.graph.EdgeData;
import program.model.graph.NodeData;
import program.model.locale.ModalConfig;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;
import program.utils.Constant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        first.add("n1");
        first.add("n2");
        first.add("n3");
        first.add("n4");
//        first.add("lim");

        second.add("e12");
        second.add("e12c");
        second.add("e12cc");
        second.add("e12ccc");
        second.add("e23");
        second.add("e24");
        second.add("sign");
        second.add("lim");

        NodeData node1 = new NodeData("source", 2, 0, 10, 0, 0, "heat", true, 0.95f);
        NodeData node3 = new NodeData("consumer", 0, 0, 0, 0, 3, "heat", true, 0.95f);
        NodeData node4 = new NodeData("consumer", 0, 0, 0, 0, 4, "heat", true, 0.95f);
        NodeData node2 = new NodeData("connector", 0, 0, 0, 0, 0, "heat", true, 0.95f);
        NodeData node5 = new NodeData("consumer", 0, 0, 0, 0, 5, "heat", true, 0.95f);

        EdgeData edge1 = new EdgeData("heat", 6, 0, 10, 10.0, "n1", "n2", false);
        EdgeData edge3 = new EdgeData("heat", 3, 0, 0, 14.0, "n2", "n3", true);
        EdgeData edge4 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n4", true);
        EdgeData edge5 = new EdgeData("heat", 10, 0, 20, 17.0, "n1", "n2", false);
        EdgeData edge6 = new EdgeData("heat", 10, 0, 50, 17.0, "n1", "n2", false);
        EdgeData edge7 = new EdgeData("heat", 10, 0, 50, 17.0, "n1", "n2", false);

        HashMap<String, NodeData> node = new HashMap<>();
        HashMap<String, EdgeData> edge = new HashMap<>();
        node.put("n1", node1);
        node.put("n2", node2);
        node.put("n3", node3);
        node.put("n4", node4);
        node.put("n5", node5);

        edge.put("e12", edge1);
        edge.put("e12c", edge5);
        edge.put("e12cc", edge6);
        edge.put("e12ccc", edge7);
        edge.put("e23", edge3);
        edge.put("e24", edge4);

        Problem problem = new Problem();
        problem.setNodeId(first);
        problem.setEdgeId(second);
        problem.setEdge(edge);
        problem.setNode(node);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String str = objectMapper.writeValueAsString(problem);
        System.out.println(str);

        MathStatement mathStatement = NonLinearStatement.getNonLinearStatement(problem);
        str = objectMapper.writeValueAsString(mathStatement);
        System.out.println(str);


        Solution solution = LinearSolver.getInstance().optimate(mathStatement);
        str = objectMapper.writeValueAsString(solution);
        System.out.println(str);
        String model = NonLinearStatement.convertModel(mathStatement);
        String data = NonLinearStatement.convertData(mathStatement);
        XmlGenerator xmlGenerator = new XmlGenerator();

//        xmlGenerator.generate(model, data);
        NeosClient.getInstance().submitProblem("C:/Users/cr4nk/IdeaProjects/Tomcat/output.xml");
    }
}