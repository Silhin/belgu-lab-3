package ru.silhin.lab_econom.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.silhin.lab_econom.Launcher;
import ru.silhin.lab_econom.database.InMemoryDatabase;
import ru.silhin.lab_econom.model.ActionCreatingModel;
import ru.silhin.lab_econom.model.ActionOperationAnalogModel;
import ru.silhin.lab_econom.model.ActionOperationProjectModel;
import ru.silhin.lab_econom.model.ActionWorkModel;
import ru.silhin.lab_econom.model.AnalogCostModel;
import ru.silhin.lab_econom.model.ConfigModel;
import ru.silhin.lab_econom.model.CostMachineTimeModel;
import ru.silhin.lab_econom.model.MachineTaskModel;
import ru.silhin.lab_econom.model.MaterialModel;
import ru.silhin.lab_econom.model.OperationMachineModel;
import ru.silhin.lab_econom.model.OtherCostModel;
import ru.silhin.lab_econom.model.QualityAnalogModel;
import ru.silhin.lab_econom.model.QualityModel;
import ru.silhin.lab_econom.model.QualityProjectModel;
import ru.silhin.lab_econom.model.WorkModel;
import ru.silhin.lab_econom.service.CreatingProjectService;
import ru.silhin.lab_econom.service.EconomicEfficiencyService;
import ru.silhin.lab_econom.service.KTYService;
import ru.silhin.lab_econom.service.OperatingCostsService;
import ru.silhin.lab_econom.service.PlanTimeService;
import ru.silhin.lab_econom.util.State;
import ru.silhin.lab_econom.util.StateMachine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class MenuController implements Initializable {
    private static InMemoryDatabase DATABASE = new InMemoryDatabase();
    private static State STATE = State.INIT;

    public AnchorPane screen1;
    public TextField hide_id_field;

    public AnchorPane screen2;
    public TextField screen2_quality_name;
    public TextField screen2_quality_koeff;
    public ListView<QualityModel> screen2_quality_names;

    public AnchorPane screen3;
    public ComboBox<QualityModel> screen3_quality_name;
    public TextField screen3_quality_score;
    public ListView<QualityProjectModel> screen3_quality_scores;

    public AnchorPane screen4;
    public ComboBox<QualityModel> screen4_quality_name;
    public TextField screen4_quality_score;
    public ListView<QualityAnalogModel> screen4_quality_scores;

    public AnchorPane screen5;
    public TextField screen5_action_name;
    public TextField screen5_action_salary;
    public ListView<ActionCreatingModel> screen5_action_names;

    public AnchorPane screen6;
    public TextField screen6_work;
    public TextField screen6_index;
    public ListView<WorkModel> screen6_works;

    public AnchorPane screen7;
    public ComboBox<WorkModel> screen7_work;
    public ComboBox<ActionCreatingModel> screen7_action;
    public TextField screen7_time;
    public ListView<ActionWorkModel> screen7_action_works;

    public AnchorPane screen8;
    public TextField screen8_material_name;
    public TextField screen8_material_count;
    public TextField screen8_material_cost;
    public CheckBox screen8_material_is_machine;
    public CheckBox screen8_material_only_creating_project;
    public ToggleGroup screen8_material_type;
    public RadioButton screen8_project;
    public RadioButton screen8_analog;
    public ListView<MaterialModel> screen8_material_names;

    public AnchorPane screen9;
    public ComboBox<MaterialModel> screen9_equipment_name;
    public TextField screen9_equipment_work_time;
    public TextField screen9_equipment_work_period;
    public ListView<MachineTaskModel> screen9_equipment_tasks;

    public AnchorPane screen10;
    public TextField screen10_machine_time;
    public TextField screen10_machine_time_cost;
    public TextField screen10_koeff_multi;

    public AnchorPane screen11;
    public ComboBox<String> screen11_cost_name;
    public TextField screen11_cost_value;
    public ListView<OtherCostModel> screen11_cost_names;

    public AnchorPane screen12;
    public ComboBox<String> screen12_cost_name;
    public TextField screen12_cost_value;
    public ListView<AnalogCostModel> screen12_cost_names;

    public AnchorPane screen13;
    public TextField screen13_action_name;
    public TextField screen13_action_salary;
    public TextField screen13_action_time;
    public ListView<ActionOperationProjectModel> screen13_action_names;

    public AnchorPane screen14;
    public TextField screen14_action_name;
    public TextField screen14_action_salary;
    public TextField screen14_action_time;
    public ListView<ActionOperationAnalogModel> screen14_action_names;

    public AnchorPane screen15;
    public ComboBox<MaterialModel> screen15_equipment;
    public TextField screen15_equipment_power;
    public TextField screen15_equipment_norm;
    public TextField screen15_equipment_koeff;
    public ListView<OperationMachineModel> screen15_equipment_list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        screen8_material_only_creating_project.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null) {
                screen8_analog.setDisable(newValue);
                screen8_project.setDisable(newValue);
                screen8_project.setSelected(true);
            }
        });
        screen2_quality_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen2_quality_name.setText(newValue.getName());
                screen2_quality_koeff.setText(String.format("%.3f", newValue.getKoeff()));
            }
        });
        screen3_quality_scores.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen3_quality_name.setValue(newValue.getQualityModel());
                screen3_quality_score.setText(String.valueOf(newValue.getScore()));
            }
        });
        screen4_quality_scores.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen4_quality_name.setValue(newValue.getQualityModel());
                screen4_quality_score.setText(String.valueOf(newValue.getScore()));
            }
        });
        screen5_action_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen5_action_name.setText(newValue.getName());
                screen5_action_salary.setText(String.format("%.2f", newValue.getSalary()));
            }
        });
        screen6_works.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen6_work.setText(newValue.getName());
                screen6_index.setText(newValue.getIndex());
            }
        });
        screen7_action_works.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen7_work.setValue(newValue.getWorkModel());
                screen7_action.setValue(newValue.getActionModel());
                screen7_time.setText(String.valueOf(newValue.getTime()));
            }
        });
        screen8_material_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen8_material_name.setText(newValue.getName());
                screen8_material_count.setText(String.valueOf(newValue.getCount()));
                screen8_material_cost.setText(String.format("%.2f", newValue.getPrice()));
                screen8_material_is_machine.setSelected(newValue.getMachine());
                screen8_material_only_creating_project.setSelected(newValue.getOnlyCreating());
                screen8_material_type.selectToggle(newValue.getType().equals(screen8_project.getText()) ? screen8_project : screen8_analog);
            }
        });
        screen9_equipment_tasks.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen9_equipment_name.setValue(newValue.getMaterialModel());
                screen9_equipment_work_time.setText(String.format("%.1f", newValue.getTime()));
                screen9_equipment_work_period.setText(String.valueOf(newValue.getDays()));
            }
        });
        screen11_cost_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen11_cost_name.setValue(newValue.getName());
                screen11_cost_value.setText(String.format("%.2f", newValue.getCost()));
            }
        });
        screen12_cost_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen12_cost_name.setValue(newValue.getName());
                screen12_cost_value.setText(String.format("%.2f", newValue.getCost()));
            }
        });
        screen13_action_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen13_action_name.setText(newValue.getName());
                screen13_action_salary.setText(String.format("%.2f", newValue.getSalary()));
                screen13_action_time.setText(String.valueOf(newValue.getTime()));
            }
        });
        screen14_action_names.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen14_action_name.setText(newValue.getName());
                screen14_action_salary.setText(String.format("%.2f", newValue.getSalary()));
                screen14_action_time.setText(String.valueOf(newValue.getTime()));
            }
        });
        screen15_equipment_list.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                hide_id_field.setText(newValue.getId().toString());
                screen15_equipment.setValue(newValue.getMaterialModel());
                screen15_equipment_power.setText(String.format("%.2f", newValue.getPower()));
                screen15_equipment_norm.setText(String.format("%.3f", newValue.getAmort()));
                screen15_equipment_koeff.setText(String.format("%.3f", newValue.getPowerKoeff()));
            }
        });

        DATABASE.drop();
        screen11_cost_name.getItems().addAll(
                "Затраты на строительство, реконструкцию здания и помещений",
                "Затраты на приобретение типовых разработок",
                "Затраты на прокладку линий связи",
                "Затраты на создание информационной базы",
                "Затраты на подготовку и переподготовку кадров"
        );
        screen12_cost_name.getItems().addAll(
                "Затраты на основное и вспомогательное оборудование",
                "Затраты на приобретение программного продукта",
                "Затраты на строительство, реконструкцию здания и помещений",
                "Затраты по оплате услуг на установку и сопровождение продукта",
                "Затраты на подготовку пользователей"
        );
        DATABASE.saveAll(List.of(
                new ConfigModel(),

                new QualityModel("Пригодность для применения"),
                new QualityModel("Точность"),
                new QualityModel("Защищенность"),
                new QualityModel("Способность к взаимодействию"),
                new QualityModel("Соответствие стандартам"),
                new QualityModel("Отсутствие ошибок"),
                new QualityModel("Устойчивость к ошибкам"),
                new QualityModel("Перезапускаемость"),
                new QualityModel("Понятность"),
                new QualityModel("Обучаемость"),
                new QualityModel("Простота использования"),
                new QualityModel("Ресурсная экономичность"),
                new QualityModel("Временная экономичность"),
                new QualityModel("Удобство для анализа"),
                new QualityModel("Изменяемость"),
                new QualityModel("Стабильность"),
                new QualityModel("Тестируемость"),
                new QualityModel("Адаптируемость"),
                new QualityModel("Структурированность"),
                new QualityModel("Замещаемость"),
                new QualityModel("Внедряемость"),

                new WorkModel("1", "Подготовка процесса разработки и анализ требований"),
                new WorkModel("1.1", "Исследование и обоснование разработки"),
                new WorkModel("1.1.1", "Постановка задачи"),
                new WorkModel("1.1.2", "Сбор исходных данных"),
                new WorkModel("1.2", "Поиск аналогов и прототипов"),
                new WorkModel("1.2.1", "Анализ существующих методов решения задачи и программных средств"),
                new WorkModel("1.2.2", "Обоснование принципиальной необходимости разработки"),
                new WorkModel("1.3", "Анализ требований"),
                new WorkModel("1.3.1", "Определение и анализ требований к проектируемой программе"),
                new WorkModel("1.3.2", "Определение структуры входных и выходных данных"),
                new WorkModel("1.3.3.", "Выбор технических и программных средств реализации"),
                new WorkModel("1.3.4", "Согласование и утверждение технического задания"),
                new WorkModel("2", "Проектирование"),
                new WorkModel("2.1", "Проектирование программной архитектуры"),
                new WorkModel("2.2", "Техническое проектирование компонентов программы"),
                new WorkModel("3", "Программирование и тестирование программных модулей"),
                new WorkModel("3.1", "Программирование модулей в выбранной среде программирования"),
                new WorkModel("3.2", "Тестирование программных модулей"),
                new WorkModel("3.3", "Сборка и испытание программы"),
                new WorkModel("3.4", "Анализ результатов испытания"),
                new WorkModel("4", "Оформление рабочей документации"),
                new WorkModel("4.1", "Проведение расчетов показателей безопасности жизнедеятельности"),
                new WorkModel("4.2", "Проведение экономических расчетов"),
                new WorkModel("4.3", "Оформление пояснительной записки")
                )
        );
    }

    public void start() {
        this.next();
    }

    public void add() {
        switch (STATE) {
            case SCREEN1 -> {
                QualityModel model = new QualityModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen2_quality_name.getText());
                model.setKoeff(Double.valueOf(screen2_quality_koeff.getText().replace(",", ".")));
                DATABASE.save(model);
                screen2_quality_names.getItems().clear();
                screen2_quality_names.getItems().addAll(DATABASE.findAll(QualityModel.class));
            }
            case SCREEN2 -> {
                QualityModel qModel = screen3_quality_name.getValue();
                QualityProjectModel model = DATABASE.findAll(QualityProjectModel.class).stream()
                        .filter(m -> m.getQualityModel().equals(qModel))
                        .findFirst().orElse(new QualityProjectModel());

                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                if (qModel != null) {
                    model.setQualityModel(qModel);
                    model.setScore(Integer.valueOf(screen3_quality_score.getText()));
                    DATABASE.save(model);
                    DATABASE.save(
                            DATABASE.findAll(QualityAnalogModel.class).stream()
                            .filter(m -> m.getQualityModel().equals(qModel))
                            .findFirst()
                            .orElse(new QualityAnalogModel(qModel, 0))
                    );
                    screen3_quality_scores.getItems().clear();
                    screen3_quality_scores.getItems().addAll(DATABASE.findAll(QualityProjectModel.class));
                }
            }
            case SCREEN3 -> {
                QualityModel qModel = screen4_quality_name.getValue();
                QualityAnalogModel model = DATABASE.findAll(QualityAnalogModel.class).stream()
                        .filter(m -> m.getQualityModel().equals(qModel))
                        .findAny().orElse(new QualityAnalogModel());
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                if (qModel != null) {
                    model.setQualityModel(qModel);
                    model.setScore(Integer.valueOf(screen4_quality_score.getText()));
                    DATABASE.save(model);
                    screen4_quality_scores.getItems().clear();
                    screen4_quality_scores.getItems().addAll(DATABASE.findAll(QualityAnalogModel.class));
                }
            }
            case SCREEN4 -> {
                ActionCreatingModel model = new ActionCreatingModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen5_action_name.getText());
                model.setSalary(Double.valueOf(screen5_action_salary.getText().replace(",", ".")));
                DATABASE.save(model);
                screen5_action_names.getItems().clear();
                screen5_action_names.getItems().addAll(DATABASE.findAll(ActionCreatingModel.class).stream().toList());
            }
            case SCREEN5 -> {
                WorkModel model = new WorkModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen6_work.getText());
                model.setIndex(screen6_index.getText());
                DATABASE.save(model);
                screen6_works.getItems().clear();
                screen6_works.getItems().addAll(DATABASE.findAll(WorkModel.class).stream().sorted(WorkModel::compare).toList());
            }
            case SCREEN6 -> {
                ActionWorkModel model = new ActionWorkModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                WorkModel workModel = screen7_work.getValue();
                ActionCreatingModel actionModel = screen7_action.getValue();
                if (workModel != null & actionModel != null) {
                    model.setWorkModel(workModel);
                    model.setActionModel(actionModel);
                    model.setTime(Integer.parseInt(screen7_time.getText()));
                    DATABASE.save(model);
                    screen7_action_works.getItems().clear();
                    screen7_action_works.getItems().addAll(DATABASE.findAll(ActionWorkModel.class).stream()
                            .sorted((o1, o2) -> WorkModel.compare(o1.getWorkModel(), o2.getWorkModel()))
                            .toList()
                    );
                }
            }
            case SCREEN7 -> {
                MaterialModel model = new MaterialModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen8_material_name.getText());
                model.setCount(Integer.valueOf(screen8_material_count.getText()));
                model.setPrice(Double.valueOf(screen8_material_cost.getText().replace(",", ".")));
                model.setMachine(screen8_material_is_machine.isSelected());
                model.setOnlyCreating(screen8_material_only_creating_project.isSelected());
                model.setType(((RadioButton) screen8_material_type.getSelectedToggle()).getText());
                DATABASE.save(model);
                if(screen8_material_type.getSelectedToggle().equals(screen8_analog)) {
                    AnalogCostModel costModel = DATABASE.findAll(AnalogCostModel.class).stream()
                            .filter(m -> m.getName().equals("Затраты на основное и вспомогательное оборудование"))
                            .findAny().orElse(new AnalogCostModel());
                    costModel.setCost(costModel.getCost() + (model.getPrice() * model.getCount()));
                    DATABASE.save(costModel);
                }
                screen8_material_names.getItems().clear();
                screen8_material_names.getItems().addAll(DATABASE.findAll(MaterialModel.class));
            }
            case SCREEN8 -> {
                MachineTaskModel model = new MachineTaskModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                MaterialModel machine = screen9_equipment_name.getValue();
                if (machine != null) {
                    model.setMaterialModel(machine);
                    model.setTime(Double.valueOf(screen9_equipment_work_time.getText().replace(",", ".")));
                    model.setDays(Integer.valueOf(screen9_equipment_work_period.getText()));
                    DATABASE.save(model);
                    screen9_equipment_tasks.getItems().clear();
                    screen9_equipment_tasks.getItems().addAll(DATABASE.findAll(MachineTaskModel.class));
                }
            }
            case SCREEN9 -> {
                List<CostMachineTimeModel> list = DATABASE.findAll(CostMachineTimeModel.class);
                CostMachineTimeModel model;
                if (list.size() == 0) {
                    model = new CostMachineTimeModel();
                } else {
                    model = list.get(0);
                }
                model.setTime(Double.valueOf(screen10_machine_time.getText().replace(",", ".")));
                model.setPrice(Double.valueOf(screen10_machine_time_cost.getText().replace(",", ".")));
                model.setKoeff(Double.valueOf(screen10_koeff_multi.getText().replace(",", ".")));
                DATABASE.save(model);
            }
            case SCREEN10 -> {
                OtherCostModel model = new OtherCostModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen11_cost_name.getValue());
                List<OtherCostModel> list = DATABASE.findAll(OtherCostModel.class);
                for (OtherCostModel item : list) {
                    if (model.getName().equals(item.getName())) {
                        model = item;
                        break;
                    }
                }
                model.setCost(Double.valueOf(screen11_cost_value.getText().replace(",", ".")));
                DATABASE.save(model);
                screen11_cost_names.getItems().clear();
                screen11_cost_names.getItems().addAll(DATABASE.findAll(OtherCostModel.class));
            }
            case SCREEN11 -> {
                AnalogCostModel model = new AnalogCostModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen12_cost_name.getValue());
                List<AnalogCostModel> list = DATABASE.findAll(AnalogCostModel.class);
                for (AnalogCostModel item : list) {
                    if (model.getName().equals(item.getName())) {
                        model = item;
                        break;
                    }
                }
                model.setCost(Double.valueOf(screen12_cost_value.getText().replace(",", ".")));
                DATABASE.save(model);
                screen12_cost_names.getItems().clear();
                screen12_cost_names.getItems().addAll(DATABASE.findAll(AnalogCostModel.class));
            }
            case SCREEN12 -> {
                ActionOperationProjectModel model = new ActionOperationProjectModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen13_action_name.getText());
                model.setSalary(Double.valueOf(screen13_action_salary.getText().replace(",", ".")));
                model.setTime(Integer.valueOf(screen13_action_time.getText()));
                DATABASE.save(model);
                screen13_action_names.getItems().clear();
                screen13_action_names.getItems().addAll(DATABASE.findAll(ActionOperationProjectModel.class));
            }
            case SCREEN13 -> {
                ActionOperationAnalogModel model = new ActionOperationAnalogModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                model.setName(screen14_action_name.getText());
                model.setSalary(Double.valueOf(screen14_action_salary.getText().replace(",", ".")));
                model.setTime(Integer.valueOf(screen14_action_time.getText()));
                DATABASE.save(model);
                screen14_action_names.getItems().clear();
                screen14_action_names.getItems().addAll(DATABASE.findAll(ActionOperationAnalogModel.class));
            }
            case SCREEN14 -> {
                OperationMachineModel model = new OperationMachineModel();
                if(!hide_id_field.getText().isEmpty()) {
                    model.setId(UUID.fromString(hide_id_field.getText()));
                }
                MaterialModel material = screen15_equipment.getValue();
                if (material != null) {
                    model.setMaterialModel(material);
                    model.setPower(Double.valueOf(screen15_equipment_power.getText().replace(",", ".")));
                    model.setPowerKoeff(Double.valueOf(screen15_equipment_koeff.getText().replace(",", ".")));
                    model.setAmort(Double.valueOf(screen15_equipment_norm.getText().replace(",", ".")));
                    DATABASE.save(model);
                    screen15_equipment_list.getItems().clear();
                    screen15_equipment_list.getItems().addAll(DATABASE.findAll(OperationMachineModel.class));
                }
            }
        }
        hide_id_field.clear();
    }

    public void next() {
        if (STATE != State.SCREEN14) {
            STATE = StateMachine.getInstance().next(STATE);
            this.view();
        } else {
            Stage stage = new Stage();
            stage.setTitle("Результаты разработки");
            WebView webView = new WebView();

            ConfigModel config = DATABASE.findAll(ConfigModel.class).get(0);
            KTYService.KTYResult ktyResult = KTYService.service(DATABASE.findAll(QualityProjectModel.class), DATABASE.findAll(QualityAnalogModel.class));
            config.setAk(ktyResult.ak());
            String htmlContent = """
                    <DOCTYPE html5>
                    <html>
                        <head>
                            <style type="text/css">
                            * {font-size: 14px;}
                            body {width: 90%; margin: 0, auto;}
                            h1 {text-align: center; font-size: 24px;}
                            h2 {font-size: 20px;}
                            table {width: 100%;}
                            table, th, td {border: 1px solid;}
                            th {font-size: 18px;}
                            table {border-collapse: collapse;}
                            </style>
                        </head>
                    <body>
                    """;
            htmlContent += ktyResult.html() + "<hr/>";
            htmlContent += PlanTimeService.service(DATABASE.findAll(ActionWorkModel.class), config) + "<hr/>";
            htmlContent += CreatingProjectService.service(
                    DATABASE.findAll(MaterialModel.class).stream().filter(MaterialModel::getOnlyCreating).toList(),
                    DATABASE.findAll(MachineTaskModel.class).stream().filter(m -> m.getMaterialModel().getOnlyCreating()).toList(),
                    DATABASE.findAll(OtherCostModel.class), DATABASE.findAll(AnalogCostModel.class),
                    DATABASE.findAll(CostMachineTimeModel.class).get(0), config
            ) + "<hr/>";
            htmlContent += OperatingCostsService.service(
                    DATABASE.findAll(ActionOperationProjectModel.class), DATABASE.findAll(AnalogCostModel.class),
                    DATABASE.findAll(OperationMachineModel.class), DATABASE.findAll(MachineTaskModel.class).stream().filter(m -> !m.getMaterialModel().getOnlyCreating()).toList(),
                    DATABASE.findAll(ActionOperationAnalogModel.class), config
            ) + "<hr/>";
            htmlContent += EconomicEfficiencyService.service(config);
            htmlContent += """
                        </body>
                    </html>
                    """;
            webView.getEngine().loadContent(htmlContent);
            stage.setScene(new Scene(webView));
            stage.show();
        }
    }

    public void back() {
        STATE = StateMachine.getInstance().back(STATE);
        this.view();
    }

    private void view() {
        this.hideAll();
        this.hide_id_field.clear();
        switch (STATE) {
            case SCREEN1 -> {
                screen2_quality_names.getItems().clear();
                screen2_quality_names.getItems().addAll(DATABASE.findAll(QualityModel.class));
                screen2.setVisible(true);
            }
            case SCREEN2 -> {
                screen3_quality_name.getItems().clear();
                screen3_quality_name.getItems().addAll(DATABASE.findAll(QualityModel.class).stream().sorted(Comparator.comparing(QualityModel::getKoeff).reversed()).toList());
                screen3_quality_scores.getItems().clear();
                screen3_quality_scores.getItems().addAll(DATABASE.findAll(QualityProjectModel.class));
                screen3.setVisible(true);
            }
            case SCREEN3 -> {
                screen4_quality_name.getItems().clear();
                screen4_quality_name.getItems().addAll(DATABASE.findAll(QualityProjectModel.class).stream().map(QualityProjectModel::getQualityModel).toList());
                screen4_quality_scores.getItems().clear();
                screen4_quality_scores.getItems().addAll(DATABASE.findAll(QualityAnalogModel.class));
                screen4.setVisible(true);
            }
            case SCREEN4 -> {
                screen5_action_names.getItems().clear();
                screen5_action_names.getItems().addAll(DATABASE.findAll(ActionCreatingModel.class).stream().toList());
                screen5.setVisible(true);
            }
            case SCREEN5 -> {
                screen6_works.getItems().clear();
                screen6_works.getItems().addAll(DATABASE.findAll(WorkModel.class).stream().sorted(WorkModel::compare).toList());
                screen6.setVisible(true);
            }
            case SCREEN6 -> {
                screen7_action.getItems().clear();
                screen7_action.getItems().addAll(DATABASE.findAll(ActionCreatingModel.class));
                screen7_work.getItems().clear();
                screen7_work.getItems().addAll(DATABASE.findAll(WorkModel.class).stream()
                        .sorted(WorkModel::compare)
                        .toList()
                );
                screen7_action_works.getItems().clear();
                screen7_action_works.getItems().addAll(DATABASE.findAll(ActionWorkModel.class).stream()
                        .sorted((o1, o2) -> WorkModel.compare(o1.getWorkModel(), o2.getWorkModel()))
                        .toList()
                );
                screen7.setVisible(true);
            }
            case SCREEN7 -> {
                screen8_material_names.getItems().clear();
                screen8_material_names.getItems().addAll(DATABASE.findAll(MaterialModel.class));
                screen8.setVisible(true);
            }
            case SCREEN8 -> {
                screen9_equipment_name.getItems().clear();
                screen9_equipment_name.getItems().addAll(DATABASE.findAll(MaterialModel.class).stream().filter(MaterialModel::getMachine).toList());
                screen9_equipment_tasks.getItems().clear();
                screen9_equipment_tasks.getItems().addAll(DATABASE.findAll(MachineTaskModel.class));
                screen9.setVisible(true);
            }
            case SCREEN9 -> {
                List<CostMachineTimeModel> list = DATABASE.findAll(CostMachineTimeModel.class);
                if (list.size() != 0) {
                    CostMachineTimeModel model = list.get(0);
                    screen10_machine_time.setText(String.valueOf(model.getTime()));
                    screen10_machine_time_cost.setText(String.valueOf(model.getPrice()));
                    screen10_koeff_multi.setText(String.valueOf(model.getKoeff()));
                }
                screen10.setVisible(true);
            }
            case SCREEN10 -> {
                screen11_cost_names.getItems().clear();
                screen11_cost_names.getItems().addAll(DATABASE.findAll(OtherCostModel.class));
                screen11.setVisible(true);
            }
            case SCREEN11 -> {
                screen12_cost_names.getItems().clear();
                screen12_cost_names.getItems().addAll(DATABASE.findAll(AnalogCostModel.class));
                screen12.setVisible(true);
            }
            case SCREEN12 -> {
                screen13_action_names.getItems().clear();
                screen13_action_names.getItems().addAll(DATABASE.findAll(ActionOperationProjectModel.class));
                screen13.setVisible(true);
            }
            case SCREEN13 -> {
                screen14_action_names.getItems().clear();
                screen14_action_names.getItems().addAll(DATABASE.findAll(ActionOperationAnalogModel.class));
                screen14.setVisible(true);
            }
            case SCREEN14 -> {
                screen15_equipment.getItems().clear();
                screen15_equipment.getItems().addAll(DATABASE.findAll(MaterialModel.class).stream().filter(m -> m.getMachine() & !m.getOnlyCreating()).toList());
                screen15_equipment_list.getItems().clear();
                screen15_equipment_list.getItems().addAll(DATABASE.findAll(OperationMachineModel.class));
                screen15.setVisible(true);
            }
        }
    }

    public void showSetting() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("pages/config-menu.fxml"));
        Scene scene = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Лабораторная работа");
        stage.setScene(scene);
        stage.show();
    }

    public void printHelp() {

    }

    public void toScreen1() {
        this.toScreen(1);
    }

    public void toScreen2() {
        this.toScreen(2);
    }

    public void toScreen3() {
        this.toScreen(3);
    }

    public void toScreen4() {
        this.toScreen(4);
    }

    public void toScreen5() {
        this.toScreen(5);
    }

    public void toScreen6() {
        this.toScreen(6);
    }

    public void toScreen7() {
        this.toScreen(7);
    }

    public void toScreen8() {
        this.toScreen(8);
    }

    public void toScreen9() {
        this.toScreen(9);
    }

    public void toScreen10() {
        this.toScreen(10);
    }

    public void toScreen11() {
        this.toScreen(11);
    }

    public void toScreen12() {
        this.toScreen(12);
    }

    public void toScreen13() {
        this.toScreen(13);
    }

    public void toScreen14() {
        this.toScreen(14);
    }

    public void toScreen(int index) {
        STATE = State.values()[index];
        this.view();
    }

    private void hideAll() {
        screen1.setVisible(false);
        screen2.setVisible(false);
        screen3.setVisible(false);
        screen4.setVisible(false);
        screen5.setVisible(false);
        screen6.setVisible(false);
        screen7.setVisible(false);
        screen8.setVisible(false);
        screen9.setVisible(false);
        screen10.setVisible(false);
        screen11.setVisible(false);
        screen12.setVisible(false);
        screen13.setVisible(false);
        screen14.setVisible(false);
        screen15.setVisible(false);
    }

    public void save(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить проект");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл проекта (*.silhin_pct)", "*.silhin_pct"));
        File saveFile = fileChooser.showSaveDialog(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            stream.writeObject(DATABASE);
        } catch (IOException e) {
            e.printStackTrace();
            Stage stage = new Stage();
            stage.setScene(new Scene(new VBox(
                    new Label("ОШИБКА ЧТЕНИЯ ФАЙЛА ПРОЕКТА!"),
                    new TextArea(e.getMessage())))
            );
            stage.setTitle("Ошибка");
            stage.show();
        }
    }

    public void load(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть проект");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл проекта (*.silhin_pct)", "*.silhin_pct"));
        File loadFile = fileChooser.showOpenDialog(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(loadFile))) {
            Object database = stream.readObject();
            if(database instanceof InMemoryDatabase) {
                DATABASE = (InMemoryDatabase) database;
            }
            this.view();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            Stage stage = new Stage();
            stage.setScene(new Scene(new VBox(
                    new Label("ОШИБКА ЧТЕНИЯ ФАЙЛА ПРОЕКТА!"),
                    new TextArea(e.getMessage())))
            );
            stage.setTitle("Ошибка");
            stage.show();
        }
    }

    public static InMemoryDatabase getDatabase() {
        return DATABASE;
    }
}
