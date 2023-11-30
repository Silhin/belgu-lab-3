package ru.silhin.lab_econom.model;

public class OperationMachineModel extends ModelObject {
    private MaterialModel materialModel;
    private Double amort;
    private Double power;
    private Double powerKoeff;

    public MaterialModel getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(MaterialModel materialModel) {
        this.materialModel = materialModel;
    }

    public Double getAmort() {
        return amort;
    }

    public void setAmort(Double amort) {
        this.amort = amort;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getPowerKoeff() {
        return powerKoeff;
    }

    public void setPowerKoeff(Double powerKoeff) {
        this.powerKoeff = powerKoeff;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f кВт, отчисления: %.3f, коэфф: %.3f", materialModel.getName(), power, amort, powerKoeff);
    }
}
