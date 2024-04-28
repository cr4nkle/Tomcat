package program.model.compressedGraph;


public class EdgeData {
    private String systemType;
    private float price;
    private int throughput;
    private int resistance;
    private int cost;
    private int maxGen;
    private int minGen;
    private int load;
    private String source;
    private String target;

    public EdgeData(){

    }

    public EdgeData(
            String systemType,
            int price,
            int throughput,
            int resistance,
            int cost,
            int maxGen,
            int minGen,
            int load,
            String source,
            String target){
        this.cost = cost;
        this.load = load;
        this.systemType = systemType;
        this.maxGen = maxGen;
        this.minGen = minGen;
        this.source = source;
        this.price = price;
        this.throughput = throughput;
        this.resistance = resistance;
        this.target = target;
    }

    public int getCost() {
        return cost;
    }

    public int getResistance() {
        return resistance;
    }

    public int getThroughput() {
        return throughput;
    }

    public String getTarget() {
        return target;
    }

    public String getSystemType() {
        return systemType;
    }

    public String getSource() {
        return source;
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
}
