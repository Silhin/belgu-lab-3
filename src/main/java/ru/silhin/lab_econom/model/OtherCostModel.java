package ru.silhin.lab_econom.model;

public class OtherCostModel extends ModelObject {
    private String name;
    private Double cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", name, cost);
    }
}
