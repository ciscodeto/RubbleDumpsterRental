module com.view.rubbledumpsterrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.naming;


    opens com.view.rubbledumpsterrental to javafx.fxml;
    exports com.view.rubbledumpsterrental;
}