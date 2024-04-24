package program.logic.calculate;

import program.model.graph.*;
import program.model.mathStatement.MathStatement;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class NodeSolution {

    public MathStatement getSolution(@NotNull Graph graph) {
        ArrayList<Integer> b = new ArrayList();
        ArrayList<Integer> max = new ArrayList();
        ArrayList<Integer> min = new ArrayList();
        ArrayList<Integer> goal = new ArrayList();

        ArrayList<Edge> edges = graph.getElement().getEdges();
        ArrayList<Node> nodes = graph.getElement().getNodes();

        b.add(0);//ноль нужен для сдвига

        nodes.forEach(node -> {
            //смотреть по пометке установлено оборудование или нет
            Data data = node.getData();
            Equipment equipment = data.getEquipment().get(0);
            int load = equipment.getLoad();
            String nodeType = data.getNodeType();

            if (nodeType.toString().equals("consumer")) {
                load = load * (-1);
                b.add(load);
            } else {
                b.add(0);
            }
        });

        b.add(1);//для ограничений на переменные
        goal.add(0);
        nodes.forEach((node -> {
            Data data = node.getData();
            ArrayList<Equipment> equipments = data.getEquipment();

            equipments.forEach(equipment -> {
                max.add(equipment.getMaxGen());
                min.add(0);
                goal.add((int) equipment.getPrice());
            });
        }));

        ArrayList<ArrayList<Integer>> matrix = new ArrayList();
        ArrayList<Node> copyNodes = new ArrayList<>();
        nodes.forEach(node -> {
            Data data = node.getData();
            String nodeType = data.getNodeType();
            copyNodes.add(node);
            if (nodeType.toString().equals("source")) {
                copyNodes.add(node);
                edges.add(edges.get(0));
            }
        });

        copyNodes.forEach(node -> {
            Data data = node.getData();
            String id = data.getId();
            ArrayList<Integer> row = new ArrayList();
            row.add(0);

            edges.forEach(edge -> {
                Data edgeData = edge.getData();
                String source = edgeData.getSource();
                String target = edgeData.getTarget();

                if (id.equals(source)) {
                    row.add(1);
                } else if (id.equals(target)) {
                    row.add(-1);
                } else {
                    row.add(0);
                }
            });
            matrix.add(row);
        });

        return new MathStatement(b, max, min, goal, matrix);
    }
}
