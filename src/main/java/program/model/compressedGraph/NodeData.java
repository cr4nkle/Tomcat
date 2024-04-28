package program.model.compressedGraph;


public class NodeData {
    private String systemType;
    private String nodeType;
    private float price;
    private int cost;
    private int maxGen;
    private int minGen;
    private int load;
    private boolean installed;

    public NodeData(){

    }

    public NodeData(
            String nodeType,
            int price,
            int cost,
            int maxGen,
            int minGen,
            int load,
            String systemType,
            boolean installed){
        this.cost = cost;
        this.load = load;
        this.nodeType = nodeType;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.price = price;
        this.systemType = systemType;
        this.installed = installed;
    }

    public int getCost() {
        return cost;
    }

    public String getNodeType() {
        return nodeType;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public int getMinGen() {
        return minGen;
    }

    public int getLoad() {
        return load;
    }

    public String getSystemType() {
        return systemType;
    }

    public boolean isInstalled() {
        return installed;
    }

    public float getPrice() {
        return price;
    }
}
