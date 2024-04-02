module migrupo.ajedrez {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens migrupo.ajedrez to javafx.fxml;
    exports migrupo.ajedrez;
    exports migrupo.ajedrez.controller;
    opens migrupo.ajedrez.controller to javafx.fxml;
}