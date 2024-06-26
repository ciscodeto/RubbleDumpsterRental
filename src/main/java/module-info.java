module com.view.rubbledumpsterrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.naming;
    requires java.management;
    requires jdk.sctp;
    requires org.apache.commons.csv;


    opens com.dumpRents.view to javafx.fxml;
    opens com.dumpRents.controller to javafx.fxml;
    opens com.dumpRents.model.entities to javafx.fxml;
    exports com.dumpRents.view;
    exports com.dumpRents.controller;
    exports com.dumpRents.model.entities;
}