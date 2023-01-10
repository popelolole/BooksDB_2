module se.kth.pellebe.booksdb_2_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;


    opens se.kth.pellebe.booksdb_2_java to javafx.fxml;
    opens se.kth.pellebe.booksdb_2_java.model to javafx.base; // open model package for reflection from PropertyValuesFactory (sigh ...)
    exports se.kth.pellebe.booksdb_2_java;
    exports se.kth.pellebe.booksdb_2_java.view;
    opens se.kth.pellebe.booksdb_2_java.view to javafx.base;
}