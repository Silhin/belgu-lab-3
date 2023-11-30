package ru.silhin.lab_econom.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import ru.silhin.lab_econom.model.ConfigModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConfigMenuController implements Initializable {
    public DatePicker startTime;
    public TextField workDaysInMonth;
    public TextField koeffAdditionalAmount;
    public TextField koeffSocialAmount;
    public TextField koeffOverheadAmount;
    public TextField electronicTariff;
    public TextField materialPercent;
    public TextField overheadPercent;
    public TextField repairPercent;
    public TextField workTime;
    public TextField koeffEconomEffectivity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigModel model = MenuController.getDatabase().findAll(ConfigModel.class).get(0);
        startTime.setValue(model.getStartTime());
        workDaysInMonth.setText(String.format("%s", model.getWorkDaysInMonth()));
        koeffAdditionalAmount.setText(String.format("%.2f", model.getAdditionalAmountCoeff() * 100));
        koeffSocialAmount.setText(String.format("%.2f", model.getSocialAmountCoeff() * 100));
        koeffOverheadAmount.setText(String.format("%.2f", model.getOverheadCostsCoeff() * 100));
        electronicTariff.setText(String.format("%.2f", model.getPowerTarif()));
        materialPercent.setText(String.format("%.2f", model.getMaterialPercent() * 100));
        overheadPercent.setText(String.format("%.2f", model.getOverheadPercent() * 100));
        repairPercent.setText(String.format("%.2f", model.getRepairNormPercent() * 100));
        workTime.setText(String.format("%.2f", model.getNormWorkTime()));
        koeffEconomEffectivity.setText(String.format("%.2f", model.getKoeffEconomEffect() * 100));
    }

    public void save() {
        ConfigModel model = MenuController.getDatabase().findAll(ConfigModel.class).get(0);
        model.setStartTime(startTime.getValue());
        model.setWorkDaysInMonth(Integer.parseInt(workDaysInMonth.getText()));
        model.setAdditionalAmountCoeff(Double.parseDouble(koeffAdditionalAmount.getText().replace(",", ".")) / 100);
        model.setSocialAmountCoeff(Double.parseDouble(koeffSocialAmount.getText().replace(",", ".")) / 100);
        model.setOverheadCostsCoeff(Double.parseDouble(koeffOverheadAmount.getText().replace(",", ".")) / 100);
        model.setOverheadPercent(Double.parseDouble(electronicTariff.getText().replace(",", ".")));
        model.setPowerTarif(Double.parseDouble(materialPercent.getText().replace(",", ".")) / 100);
        model.setRepairNormPercent(Double.parseDouble(overheadPercent.getText().replace(",", ".")) / 100);
        model.setMaterialPercent(Double.parseDouble(repairPercent.getText().replace(",", ".")) / 100);
        model.setNormWorkTime(Double.parseDouble(workTime.getText().replace(",", ".")));
        model.setKoeffEconomEffect(Double.parseDouble(koeffEconomEffectivity.getText().replace(",", ".")) / 100);
        MenuController.getDatabase().save(model);
    }

    public void reset() {
        ConfigModel model = MenuController.getDatabase().findAll(ConfigModel.class).get(0);
        model.setStartTime(LocalDate.now());
        model.setWorkDaysInMonth(21);
        model.setAdditionalAmountCoeff(0.4);
        model.setSocialAmountCoeff(0.302);
        model.setOverheadCostsCoeff(0.6);
        model.setOverheadPercent(0.2);
        model.setPowerTarif(5.0);
        model.setRepairNormPercent(0.05);
        model.setMaterialPercent(0.01);
        model.setNormWorkTime(8.0);
        model.setKoeffEconomEffect(0.33);
        MenuController.getDatabase().save(model);
    }
}
