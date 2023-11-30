package ru.silhin.lab_econom.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ConfigModel extends ModelObject {
    private LocalDate startTime = LocalDate.now();
    private Integer workDaysInMonth = 21;
    private Double additionalAmountCoeff = 0.4;
    private Double socialAmountCoeff = 0.302;
    private Double overheadCostsCoeff = 0.6;
    private Double overheadPercent = 0.2;
    private Double powerTarif = 5.0;
    private Double repairNormPercent = 0.05;
    private Double materialPercent = 0.01;
    private Double normWorkTime = 8.0;
    private Double koeffEconomEffect = 0.33;

    // Hided
    private Double ak = .0;
    private Double totalAmountProject = .0;
    private Double totalAmountAnalog = .0;
    private Double totalAmountProjectOperation = .0;
    private Double totalAmountAnalogOperation = .0;
    private Map<ActionCreatingModel, Integer> times = new HashMap<>();

    public Double getAk() {
        return ak;
    }

    public void setAk(Double ak) {
        this.ak = ak;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public Map<ActionCreatingModel, Integer> getTimes() {
        return times;
    }

    public void setTimes(Map<ActionCreatingModel, Integer> times) {
        this.times = times;
    }

    public Integer getWorkDaysInMonth() {
        return workDaysInMonth;
    }

    public void setWorkDaysInMonth(Integer workDaysInMonth) {
        this.workDaysInMonth = workDaysInMonth;
    }

    public Double getAdditionalAmountCoeff() {
        return additionalAmountCoeff;
    }

    public void setAdditionalAmountCoeff(Double additionalAmountCoeff) {
        this.additionalAmountCoeff = additionalAmountCoeff;
    }

    public Double getSocialAmountCoeff() {
        return socialAmountCoeff;
    }

    public void setSocialAmountCoeff(Double socialAmountCoeff) {
        this.socialAmountCoeff = socialAmountCoeff;
    }

    public Double getOverheadCostsCoeff() {
        return overheadCostsCoeff;
    }

    public void setOverheadCostsCoeff(Double overheadCostsCoeff) {
        this.overheadCostsCoeff = overheadCostsCoeff;
    }

    public Double getTotalAmountProject() {
        return totalAmountProject;
    }

    public void setTotalAmountProject(Double totalAmountProject) {
        this.totalAmountProject = totalAmountProject;
    }

    public Double getTotalAmountAnalog() {
        return totalAmountAnalog;
    }

    public void setTotalAmountAnalog(Double totalAmountAnalog) {
        this.totalAmountAnalog = totalAmountAnalog;
    }

    public Double getTotalAmountProjectOperation() {
        return totalAmountProjectOperation;
    }

    public void setTotalAmountProjectOperation(Double totalAmountProjectOperation) {
        this.totalAmountProjectOperation = totalAmountProjectOperation;
    }

    public Double getTotalAmountAnalogOperation() {
        return totalAmountAnalogOperation;
    }

    public void setTotalAmountAnalogOperation(Double totalAmountAnalogOperation) {
        this.totalAmountAnalogOperation = totalAmountAnalogOperation;
    }

    public Double getOverheadPercent() {
        return overheadPercent;
    }

    public void setOverheadPercent(Double overheadPercent) {
        this.overheadPercent = overheadPercent;
    }

    public Double getMaterialPercent() {
        return materialPercent;
    }

    public void setMaterialPercent(Double materialPercent) {
        this.materialPercent = materialPercent;
    }

    public Double getNormWorkTime() {
        return normWorkTime;
    }

    public void setNormWorkTime(Double normWorkTime) {
        this.normWorkTime = normWorkTime;
    }

    public Double getPowerTarif() {
        return powerTarif;
    }

    public void setPowerTarif(Double powerTarif) {
        this.powerTarif = powerTarif;
    }

    public Double getRepairNormPercent() {
        return repairNormPercent;
    }

    public void setRepairNormPercent(Double repairNormPercent) {
        this.repairNormPercent = repairNormPercent;
    }

    public Double getKoeffEconomEffect() {
        return koeffEconomEffect;
    }

    public void setKoeffEconomEffect(Double koeffEconomEffect) {
        this.koeffEconomEffect = koeffEconomEffect;
    }

    @Override
    public String toString() {
        return "";
    }

}
