package se.kth.pellebe.booksdb_2_java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import se.kth.pellebe.booksdb_2_java.model.*;

public class ConnectDialog extends Dialog<String[]> {
    private final TextField databaseField = new TextField();
    private final TextField userField = new TextField();
    private final TextField pwdField = new TextField();
    private final BooksDbInterface booksDb;

    public ConnectDialog(Controller controller, BooksDbInterface booksDb){
        this.booksDb = booksDb;
        buildUpdateDialog(controller);
    }

    private void buildUpdateDialog(Controller controller) {

        this.setTitle("Login");
        this.setResizable(false); // really?

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Database"), 1, 1);
        grid.add(databaseField, 2, 1);
        grid.add(new Label("Username"), 1, 2);
        grid.add(userField, 2, 2);
        grid.add(new Label("Password"), 1, 3);
        grid.add(pwdField, 2, 3);

        ButtonType buttonTypeOk
                = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(new Callback<ButtonType, String[]>() {
            @Override
            public String[] call(ButtonType b) {
                String[] result = new String[3];
                if (b == buttonTypeOk) {
                    result[0] = databaseField.getText();
                    result[1] = userField.getText();
                    result[2] = pwdField.getText();
                }
                clearFormData();
                return result;
            }
        });

        this.getDialogPane().setContent(grid);
    }

    private void clearFormData() {
        userField.setText("");
        pwdField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
