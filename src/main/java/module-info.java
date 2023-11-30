module ru.silhin.lab_econom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens ru.silhin.lab_econom to javafx.fxml;
    exports ru.silhin.lab_econom;
    exports ru.silhin.lab_econom.controller;
    exports ru.silhin.lab_econom.database;
    exports ru.silhin.lab_econom.model;
    opens ru.silhin.lab_econom.controller to javafx.fxml;
}