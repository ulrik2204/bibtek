package bibtek.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import bibtek.core.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateUserController implements Initializable {

    @FXML
    Button createUserButton;

    @FXML
    TextField userNameInput;

    @FXML
    TextField userNameConfirmInput;

    @FXML
    TextField ageInput;

    @FXML
    CheckBox confirmCheckbox;

    @FXML
    Label termsLabel;

    @FXML
    Label errorLabel;

    /**
     * Initializes the create user page with appropriate functions.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        ageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        termsLabel.setOnMouseClicked((agent) -> this.showTerms());

    }

    /**
     * Shows the terms and conditions to the user.
     */
    @FXML
    public void showTerms() {

        final Stage stage = new Stage();

        final Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/bibtek/ui/Terms.fxml"));
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates the and saves user.
     */
    @FXML
    public void createUser() {
        String userName1 = userNameInput.getText();
        String userName2 = userNameConfirmInput.getText();
        if (!userName1.equals(userName2)) {
            errorLabel.setText("User names don't match");
            errorLabel.setTextFill(Color.RED);
            return;
        }
        final int age = Integer.parseInt(ageInput.getText());

        if (age < User.MINIMAL_AGE) {
            errorLabel.setText("You must be at least 13 \nto create an account");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        if (!confirmCheckbox.isSelected()) {
            errorLabel.setText("You must consent to the terms \nto create an account");
            errorLabel.setTextFill(Color.RED);
            return;
        }

        User user = new User(userName1, age);
        user.saveUser();

        // Insert code for feedback that the user was created.

        final Stage stage = (Stage) createUserButton.getScene().getWindow();
        final Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/bibtek/ui/LoginPage.fxml"));
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
