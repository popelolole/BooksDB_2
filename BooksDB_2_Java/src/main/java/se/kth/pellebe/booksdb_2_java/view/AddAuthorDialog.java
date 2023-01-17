package se.kth.pellebe.booksdb_2_java.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import se.kth.pellebe.booksdb_2_java.model.Author;
import se.kth.pellebe.booksdb_2_java.model.Book;
import se.kth.pellebe.booksdb_2_java.model.BooksDbException;
import se.kth.pellebe.booksdb_2_java.model.BooksDbInterface;

import java.util.List;

public class AddAuthorDialog extends Dialog<Author> {
    private final TextField nameField = new TextField();
    private final TextField dobField = new TextField();
    private final TextField booksField = new TextField();
    private final BooksDbInterface booksDb;

    public AddAuthorDialog(Controller controller, BooksDbInterface booksDb) {
        this.booksDb = booksDb;
        buildAddBookDialog(controller);
    }

    private void buildAddBookDialog(Controller controller) {

        this.setTitle("Add a new author");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Name "), 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(new Label("Date of birth "), 1, 2);
        grid.add(dobField, 2, 2);
        grid.add(new Label("Books "), 1, 3);
        grid.add(booksField, 2, 3);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(new Callback<ButtonType, Author>() {
            @Override
            public Author call(ButtonType b) {
                Author result = null;
                if (b == buttonTypeOk) {
                    result = new Author(nameField.getText(),
                            dobField.getText());
                    String[] booksNames = booksField.getText().split(", ");
                    for(int currentBook = 0;currentBook < booksNames.length;currentBook++) {
                        if(booksNames[currentBook] != null) {
                            result.addBook(new Book(booksNames[currentBook]));
                        }
                    }
                }

                clearFormData();
                return result;
            }
        });
    }

    private void clearFormData() {
        nameField.setText("");
        dobField.setText("");
        booksField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
