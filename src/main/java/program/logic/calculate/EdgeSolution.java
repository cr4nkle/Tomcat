package program.logic.calculate;

import program.model.graph.*;
import program.model.mathStatement.MathStatement;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class EdgeSolution {

    public MathStatement getSolution (@NotNull Graph graph) {
        ArrayList<Integer> b = new ArrayList();
        ArrayList<Integer> max = new ArrayList();
        ArrayList<Integer> min = new ArrayList();
        ArrayList<Integer> goal = new ArrayList();

        ArrayList<Edge> edges = graph.getElement().getEdges();
        ArrayList<Node> nodes = graph.getElement().getNodes();

        nodes.forEach(node -> {
            Data data = node.getData();
            Equipment equipment = data.getEquipment().get(0);
            int maxGen = equipment.getMaxGen();
            int load = equipment.getLoad();
            String nodeType = data.getNodeType();

            if (nodeType.toString().equals("source")) {
                b.add(maxGen);
            }else if (nodeType.toString().equals("consumer")) {
                load = load * (-1);
                b.add(load);
            }else {
                b.add(0);
            }
        });

        edges.forEach((edge -> {
            Data data = edge.getData();
            int length = data.getLength();
            ArrayList<Equipment> equipments = data.getEquipment();

            equipments.forEach(equipment -> {
                max.add(equipment.getThroughput());
                min.add(0);
                goal.add(equipment.getCost() * length);
            });
        }));

        ArrayList<ArrayList<Integer>> matrix = new ArrayList();

        nodes.forEach(node -> {
            Data data = node.getData();
            String id = data.getId();
            ArrayList<Integer> row = new ArrayList();

            edges.forEach(edge -> {
                Data edgeData = edge.getData();
                String source = edgeData.getSource();
                String target = edgeData.getTarget();
                ArrayList<Equipment> equipments = edgeData.getEquipment();

                equipments.forEach(equipment -> {
                    if (id.equals(source)) {
                        row.add(1);
                    } else if (id.equals(target)) {
                        row.add(-1);
                    } else {
                        row.add(0);
                    }
                });
            });
            matrix.add(row);
        });

        return new MathStatement(b, max, min, goal, matrix);
    }
}
