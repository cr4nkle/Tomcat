package org.example;

import java.util.List;

public class Edge {
    private String id;
    private String nodetype;
    private String systemtype;
    private String source;
    private String target;
    private List<Equipment> equipment;

    public Edge() {}

    public Edge(String id, String nodetype, String systemtype, List<Equipment> equipment) {
        this.id = id;
        this.nodetype = nodetype;
        this.systemtype = systemtype;
        this.equipment = equipment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodetype() {
        return nodetype;
    }

    public void setNodetype(String nodetype) {
        this.nodetype = nodetype;
    }

    public String getSystemtype() {
        return systemtype;
    }

    public void setSystemtype(String systemtype) {
        this.systemtype = systemtype;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", nodetype='" + nodetype + '\'' +
                ", systemtype='" + systemtype + '\'' +
                ", equipment=" + equipment +
                '}';
    }


    public class Equipment {
        private String label;
        private int value;

        public Equipment() {}

        public Equipment(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Equipment{" +
                    "label='" + label + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

}
