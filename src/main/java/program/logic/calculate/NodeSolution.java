package program.logic.calculate;

import program.model.graph.Data;
import program.model.graph.Equipment;
import program.model.graph.Graph;
import program.model.graph.Node;
import program.model.mathStatement.MathStatement;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class NodeSolution {

    public MathStatement getSolution (@NotNull Graph graph){
        ArrayList<Integer> b = new ArrayList();
        ArrayList<Integer> max = new ArrayList();
        ArrayList<Integer> min = new ArrayList();
        ArrayList<Integer> goal = new ArrayList();

        ArrayList<Node> nodes = graph.getElement().getNodes();
        final int[] sumLoad = {0};

        nodes.forEach(node -> {
            Data data = node.getData();
            ArrayList<Equipment> equipments = data.getEquipment();
            String nodeType = data.getNodeType();

            if (nodeType.equals("источник")) {
                equipments.forEach(equipment -> {
                    goal.add(equipment.getCost());
                });
            }
        });


        ArrayList<ArrayList<Integer>> matrix = new ArrayList();

        return new MathStatement(b, max, min, goal, matrix);
    }
}
