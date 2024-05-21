module com.view.rubbledumpsterrentalsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens view to javafx.fxml;
    exports view;
}