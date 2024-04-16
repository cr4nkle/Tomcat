package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonReader {
    public ArrayList<ArrayList<String>> readJson(String object){
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(object);
            System.out.println(obj);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = (JSONObject) obj;
        JSONObject elements = (JSONObject) jsonObject.get("elements");
        JSONArray nodes = (JSONArray) elements.get("nodes");
        JSONArray edges = (JSONArray) elements.get("edges");

        ArrayList<String> b = new ArrayList();
        ArrayList<String> max = new ArrayList();
        ArrayList<String> min = new ArrayList();
        ArrayList<String> goal = new ArrayList();
        ArrayList<ArrayList<String>> rezVect = new ArrayList();

        nodes.forEach((node) -> {
            JSONObject data = (JSONObject) ((JSONObject) node).get("data");
            JSONArray equipments = (JSONArray) data.get("equipment");
            //сделать проверку на длину массива если больше 1 то формируем мат постановку если нет то уходим дальше
            Object equipment = equipments.get(0);
            Object value = ((JSONObject) equipment).get("value");
            Object nodeType = data.get("nodetype");

            if (nodeType.toString().equals("потребитель")) {
                value = -(Integer.parseInt(value.toString()));
            }
            b.add(value.toString());
        });

        edges.forEach((edge) -> {
            JSONObject data = (JSONObject) ((JSONObject) edge).get("data");
            JSONArray equipments = (JSONArray) data.get("equipment");

            equipments.forEach((equipment) -> {

                Object maxValue = ((JSONObject) equipment).get("max_value");
                Object minValue = ((JSONObject) equipment).get("min_value");
                Object price = ((JSONObject) equipment).get("price");
                max.add(maxValue.toString());
                min.add(minValue.toString());
                goal.add(price.toString());
            });
        });

        ArrayList<ArrayList<String>> matrix = new ArrayList();

        nodes.forEach((node) -> {
            JSONObject nodeData = (JSONObject) ((JSONObject) node).get("data");
            Object nodeId = nodeData.get("id");
//                System.out.println(nodeId);
            ArrayList<String> row = new ArrayList();
            edges.forEach((edge) -> {
                JSONObject edgeData = (JSONObject) ((JSONObject) edge).get("data");
                Object edgeSrcId = edgeData.get("source");
                Object edgeTrgId = edgeData.get("target");
                JSONArray equipments = (JSONArray) edgeData.get("equipment");

                equipments.forEach((t) -> {
                    if (nodeId.equals(edgeSrcId)) {
                        row.add("1");
                    } else if (nodeId.equals(edgeTrgId)) {
                        row.add("-1");
                    } else {
                        row.add("0");
                    }
                });
            });
            matrix.add(row);
        });

        rezVect.add(b);
        rezVect.add(max);
        rezVect.add(min);
        rezVect.add(goal);

        rezVect.forEach((item) -> {
            item.forEach((el) -> {
                System.out.print(el);
                System.out.print(" ");
            });
            System.out.println();
        });

        System.out.println();
//
        matrix.forEach((item) -> {
            item.forEach((el) -> {
                System.out.print(el);
                System.out.print(" ");
            });
            System.out.println();
        });
        return rezVect;
    }

}
