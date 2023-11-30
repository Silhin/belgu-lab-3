package ru.silhin.lab_econom.model;

public class QualityModel extends ModelObject {
    private String name;
    private Double koeff;

    public QualityModel() {
        super();
    }

    public QualityModel(String name) {
        this(name, .0);
    }

    public QualityModel(String name, Double koeff) {
        super();
        this.name = name;
        this.koeff = koeff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getKoeff() {
        return koeff;
    }

    public void setKoeff(Double koeff) {
        this.koeff = koeff;
    }

    @Override
    public String toString() {
        return String.format("%s [%.3f]", name, koeff);
    }
}
