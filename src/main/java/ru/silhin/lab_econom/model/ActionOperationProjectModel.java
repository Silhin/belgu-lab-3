package ru.silhin.lab_econom.model;

public class ActionOperationProjectModel extends ActionCreatingModel {
    private Integer time;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s [%.2f руб | %s дней]", this.getName(), this.getSalary(), this.getTime());
    }
}
