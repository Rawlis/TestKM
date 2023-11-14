module qwe111 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
    

    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    
    opens application to javafx.graphics, javafx.fxml;
}