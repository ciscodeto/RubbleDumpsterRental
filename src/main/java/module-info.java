module com.view.rubbledumpsterrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.naming;
    requires java.management;
    requires jdk.sctp;
    requires org.apache.commons.csv;


    opens com.view to javafx.fxml;
    exports com.view;
}