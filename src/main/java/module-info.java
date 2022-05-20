module com.courseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.gemsfx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;


    opens com.courseproject to javafx.fxml;
    exports com.courseproject;
}