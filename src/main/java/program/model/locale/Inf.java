package program.model.locale;

public class Inf {
    private String name;
    private String capCosts;
    private String price;
    private String genMax;
    private String genMin;

    public Inf() {
    }

    public Inf(
            String name,
            String capCosts,
            String price,
            String genMax,
            String genMin
    ) {
        this.name = name;
        this.capCosts = capCosts;
        this.price = price;
        this.genMax = genMax;
        this.genMin = genMin;
    }

    public String getName() {
        return name;
    }

    public String getCapCosts() {
        return capCosts;
    }

    public String getPrice() {
        return price;
    }

    public String getGenMax() {
        return genMax;
    }

    public String getGenMin() {
        return genMin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapCosts(String capCosts) {
        this.capCosts = capCosts;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setGenMax(String genMax) {
        this.genMax = genMax;
    }

    public void setGenMin(String genMin) {
        this.genMin = genMin;
    }
}
