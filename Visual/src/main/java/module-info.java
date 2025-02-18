module com.example.visual {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires java.desktop;


    opens com.example.visual to javafx.fxml;
    exports com.example.visual;
}