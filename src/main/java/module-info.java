module org.arwani {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires json;
    requires twitter.api.java.sdk;
    requires java.sql;

    opens org.arwani to javafx.fxml;
    exports org.arwani;


}

