package program.model.locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Checkboxes {
    private String heat;
    private String power;
    private String water;
    private String fuel;
    private String block;
    private String all;

    public Checkboxes() {
    }

    public Checkboxes(
            String heat,
            String power,
            String water,
            String fuel,
            String block,
            String all
    ) {
        this.heat = heat;
        this.power = power;
        this.water = water;
        this.fuel = fuel;
        this.block = block;
        this.all = all;
    }

    public String getHeat() {
        return heat;
    }

    public String getPower() {
        return power;
    }

    public String getWater() {
        return water;
    }

    public String getFuel() {
        return fuel;
    }

    public String getBlock() {
        return block;
    }

    public String getAll() {
        return all;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
