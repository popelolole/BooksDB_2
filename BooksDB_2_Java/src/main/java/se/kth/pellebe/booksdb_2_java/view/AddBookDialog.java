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
import se.kth.pellebe.booksdb_2_java.model.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddBookDialog extends Dialog<Book> {

    private final TextField titleField = new TextField();
    private final TextField authorField = new TextField();
    private final TextField isbnField = new TextField();
    private final TextField publishedField = new TextField();
    private final TextField ratingField = new TextField();
    private final ComboBox<Book.Genre> genreChoice = new ComboBox(FXCollections
            .observableArrayList(Book.Genre.values()));
    private final BooksDbInterface booksDb;

    public AddBookDialog(Controller controller, BooksDbInterface booksDb) {
        this.booksDb = booksDb;
        buildAddBookDialog(controller);
    }

    private void buildAddBookDialog(Controller controller) {

        this.setTitle("Add a new book");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Title "), 1, 1);
        grid.add(titleField, 2, 1);
        grid.add(new Label("Author"), 1, 2);
        grid.add(authorField, 2, 2);
        grid.add(new Label("Isbn "), 1, 3);
        grid.add(isbnField, 2, 3);
        grid.add(new Label("Published "), 1, 4);
        grid.add(publishedField, 2, 4);
        grid.add(new Label("Rating "), 1, 5);
        grid.add(ratingField, 2, 5);
        grid.add(new Label("Genre "), 1, 6);
        grid.add(genreChoice, 2, 6);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(new Callback<ButtonType, Book>() {
            @Override
            public Book call(ButtonType b) {
                Book result = null;
                if (b == buttonTypeOk) {
                    if (isValidData()) {
                        result = new Book(isbnField.getText(),
                                titleField.getText(),
                                publishedField.getText(),
                                Double.parseDouble(ratingField.getText()),
                                genreChoice.getValue());
                        try {
                            String[] authorsNames = authorField.getText().split(", ");
                            for(int currentAuthor = 0;currentAuthor < authorsNames.length;currentAuthor++) {
                                List<Book> books = booksDb.searchBooksByAuthor(authorsNames[currentAuthor]);
                                if (books.size() != 0) {
                                    for (int currentBook = 0;currentBook < books.size();currentBook++) {
                                        Book book = books.get(currentBook);
                                        Author author = book.getAuthors().get(currentBook);
                                        if (author.getName().equals(authorsNames[currentAuthor])) {
                                            result.addAuthor(new Author(author.getName(),
                                                    author.getDob()));
                                            break;
                                        }
                                    }
                                } else {
                                    if(authorsNames[currentAuthor] != null) {
                                        result.addAuthor(new Author(authorsNames[currentAuthor]));
                                    }
                                }
                            }
                        }catch(BooksDbException e){
                            showErrorAlert("Input Error", "Invalid input.");
                        }
                    }
                }

                clearFormData();
                return result;
            }
        });

        // add an event filter to keep the dialog active if validation fails
        // (yes, this is ugly in FX)
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
    }

    private boolean isValidData() {
        if (genreChoice.getValue() == null) {
            return false;
        }
        if (!Book.isValidIsbn(isbnField.getText())) {
            System.out.println(isbnField.getText());
            return false;
        }

        return true;
    }

    private void clearFormData() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        publishedField.setText("");
        ratingField.setText("");
        genreChoice.setValue(null);
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
