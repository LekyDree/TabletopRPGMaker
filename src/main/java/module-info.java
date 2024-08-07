module com.example.tabletoprpgmaker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires org.graalvm.sdk;

    opens com.example.tabletoprpgmaker to javafx.fxml;
    exports com.example.tabletoprpgmaker;
}