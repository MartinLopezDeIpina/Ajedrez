module migrupo.ajedrez {
    requires javafx.controls;
    requires javafx.fxml;


    opens migrupo.ajedrez to javafx.fxml;
    exports migrupo.ajedrez;
}