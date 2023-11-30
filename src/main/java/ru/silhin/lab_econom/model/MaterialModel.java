package ru.silhin.lab_econom.model;

public class MaterialModel extends ModelObject {
    private String name;
    private Integer count;
    private Double price;
    private Boolean machine;
    private Boolean onlyCreating;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getMachine() {
        return machine;
    }

    public void setMachine(Boolean machine) {
        this.machine = machine;
    }

    public Boolean getOnlyCreating() {
        return onlyCreating;
    }

    public void setOnlyCreating(Boolean onlyCreating) {
        this.onlyCreating = onlyCreating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s: %s [%s] {%.2f руб/шт} [Использование: %s%s]", machine ? "Оборудование" : "Материал", name, count, price, type, onlyCreating ? " - Разработка" : "");
    }
}
