package ru.silhin.lab_econom.model;

public class ActionCreatingModel extends ModelObject {
    private String name;
    private Double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%s [Оклад: %.2f руб]", name, salary);
    }
}
