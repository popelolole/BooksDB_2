package se.kth.pellebe.booksdb_2_java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.pellebe.booksdb_2_java.model.*;
import se.kth.pellebe.booksdb_2_java.view.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BooksDbImpl booksDb = new BooksDbImpl(); // model

        BooksPane root = new BooksPane(booksDb, primaryStage);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Books Database Client");
        // add an exit handler to the stage (X) ?
        primaryStage.setOnCloseRequest(event -> {
            try {
                booksDb.disconnect();
            } catch (Exception e) {}
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
