package se.kth.pellebe.booksdb_2_java.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import se.kth.pellebe.booksdb_2_java.model.*;

import java.util.List;

public class RemoveDialog extends Dialog<Book> {
    private final TextField titleField = new TextField();
    private final TextField authorField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField publishedField = new TextField();
    private final TextField ratingField = new TextField();
    private final BooksDbInterface booksDb;

    public RemoveDialog(Controller controller, BooksDbInterface booksDb){
        this.booksDb = booksDb;
        buildRemoveDialog(controller);
    }

    private void buildRemoveDialog(Controller controller) {

        this.setTitle("Remove Book");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Choose Book (ISBN) "), 1, 1);
        grid.add(isbnField, 2, 1);

        ButtonType buttonTypeOk
                = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(new Callback<ButtonType, Book>() {
            @Override
            public Book call(ButtonType b) {
                Book result = null;
                if (b == buttonTypeOk) {
                    try {
                        List<Book> books = booksDb.searchBooksByISBN(isbnField.getText());
                        if (books.get(0) != null) {
                            result = books.get(0);
                        } else {
                            showErrorAlert("Input Error", "Not a valid isbn.");
                        }
                    }catch(BooksDbException e) {}
                }
                clearFormData();
                return result;
            }
        });

        Button okButton
                = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
        okButton.addEventFilter(ActionEvent.ACTION, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!isValidData()) {
                    event.consume();
                    showErrorAlert("Form error", "Invalid input");
                }
            }
        });

        this.getDialogPane().setContent(grid);
    }

    private boolean isValidData() {
        if (!Book.isValidIsbn(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }
        // if(...) - keep on validating user input...

        return true;
    }

    private void clearFormData() {
        isbnField.setText("");
        ratingField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
