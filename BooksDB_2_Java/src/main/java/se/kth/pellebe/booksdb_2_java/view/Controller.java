package se.kth.pellebe.booksdb_2_java.view;

import javafx.application.Platform;
import javafx.stage.Stage;
import se.kth.pellebe.booksdb_2_java.model.*;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.*;

/**
 * The controller is responsible for handling user requests and update the view
 * (and in some cases the model).
 *
 * @author pellebe@kth.se
 * @author eabraham@kth.se
 */
public class Controller {

    private final Stage primaryStage;
    private final BooksPane booksView; // view
    private final BooksDbInterface booksDb; // model

    public Controller(Stage primaryStage, BooksDbInterface booksDb, BooksPane booksView) {
        this.primaryStage = primaryStage;
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    public void handleExit(){
        handleDisconnect();
        primaryStage.close();
    }

    public void handleConnect(String[] login){
        String database = login[0];
        String user = login[1];
        String pwd = login[2];
        try {
            if(booksDb.connect(database, user, pwd))
                booksView.showAlertAndWait("Connection Successful.", INFORMATION);
            else
                booksView.showAlertAndWait("Connection Failed.", INFORMATION);
        }catch(BooksDbException e){
            booksView.showAlertAndWait("Connection Error.",ERROR);
        }
    }

    public void handleDisconnect(){
        try {
            booksDb.disconnect();
            booksView.showAlertAndWait("Disconnected.", INFORMATION);
        }
        catch(BooksDbException e){
            booksView.showAlertAndWait("Error Disconnecting.",ERROR);
        }
    }



    protected void onSearchSelected(String searchFor, SearchMode mode) throws BooksDbException{
        try {
            if (searchFor != null && searchFor.length() > 1) {
                Thread t = new Thread(() -> {
                    final List<Book> result;
                    try {
                        switch (mode) {
                            case Title:
                                result = booksDb.searchBooksByTitle(searchFor);
                                break;
                            case ISBN:
                                result = booksDb.searchBooksByISBN(searchFor);
                                break;
                            case Author:
                                result = booksDb.searchBooksByAuthor(searchFor);
                                break;
                            case Genre:
                                result = booksDb.searchBooksByGenre(searchFor);
                                break;
                            case Rating:
                                result = booksDb.searchBooksByRating(searchFor);
                                break;
                            default:
                                result = new ArrayList<>();
                        }

                    Platform.runLater(() -> {
                        if (result == null || result.isEmpty()) {
                            booksView.showAlertAndWait(
                                    "No results found.", INFORMATION);
                        } else {
                            booksView.displayBooks(result);
                        }
                    });
                    }catch(BooksDbException e){
                        booksView.showAlertAndWait("Search Error.",ERROR);
                    }
                });
                t.start();
            }
            else {
                booksView.showAlertAndWait(
                        "Enter a search string!", WARNING);
            }
        } catch (Exception e) {
            booksView.showAlertAndWait("Database error.",ERROR);
        }
    }

    public void handleInsertBook(Book book){
        try {
            booksDb.insertBook(book);
        }catch(BooksDbException e){
            booksView.showAlertAndWait("Input error.",ERROR);
        }
    }

    public void handleUpdateBook(Book book){
        Thread t = new Thread(() -> {
            try {
                booksDb.updateBook(book);
            } catch(BooksDbException e){
                booksView.showAlertAndWait("Update error.",ERROR);
            }
        });
        t.start();
    }

    public void handleRemoveBook(Book book){
        Thread t = new Thread(() -> {
            try {
                booksDb.removeBook(book);

            } catch(BooksDbException e){
                booksView.showAlertAndWait("Update error.",ERROR);
            }
        });
        t.start();
    }
}
