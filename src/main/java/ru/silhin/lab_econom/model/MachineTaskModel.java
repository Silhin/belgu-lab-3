package ru.silhin.lab_econom.model;

public class MachineTaskModel extends ModelObject {
    private MaterialModel materialModel;
    private Double time;
    private Integer days;

    public MaterialModel getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(MaterialModel materialModel) {
        this.materialModel = materialModel;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return String.format("%s: [%.1f, %s]", materialModel.getName(), time, days);
    }
}
