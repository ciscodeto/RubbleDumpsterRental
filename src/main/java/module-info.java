module com.view.rubbledumpsterrental {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.view.rubbledumpsterrental to javafx.fxml;
    exports com.view.rubbledumpsterrental;
}