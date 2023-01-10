module se.kth.pellebe.booksdb_2_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;


    opens se.kth.pellebe.booksdb_2_java to javafx.fxml;
    exports se.kth.pellebe.booksdb_2_java;
}