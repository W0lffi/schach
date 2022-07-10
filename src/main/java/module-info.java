module schach {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
    
    exports schach;
    opens schach;
    exports controller;
    opens controller;
    exports model;
    opens model;
    exports view;
    opens view;
    exports stubs;
    opens stubs;
}