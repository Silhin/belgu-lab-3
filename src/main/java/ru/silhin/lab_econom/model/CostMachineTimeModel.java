package ru.silhin.lab_econom.model;

public class CostMachineTimeModel extends ModelObject {
    private Double time;
    private Double price;
    private Double koeff;

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getKoeff() {
        return koeff;
    }

    public void setKoeff(Double koeff) {
        this.koeff = koeff;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.2f руб, коэфф: %.3f", time, price, koeff);
    }
}
