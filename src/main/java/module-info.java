module org.example.test.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires stripe.java;
    requires okhttp3;
    requires com.azure.core.experimental;
    requires com.google.gson;

    opens org.example.test.test.models to javafx.base;
    opens org.example.test.test to javafx.fxml;
    exports org.example.test.test;
    exports org.example.test.test.ActionConroller;
    opens org.example.test.test.ActionConroller to javafx.fxml;
    //exports org.example.test.test.AssociationFXMLControllers;
    //opens org.example.test.test.AssociationFXMLControllers to javafx.fxml;
}