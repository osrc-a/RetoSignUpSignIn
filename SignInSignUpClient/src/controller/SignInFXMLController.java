/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import userinterfacetier.SignUpSignIn;

public class SignInFXMLController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPsswd;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

    @FXML
    private void handleLogin() throws Exception {
  String email = txtEmail.getText();
        String password = txtPsswd.getText();
        // Validar los campos y obtener el mensaje de error, si existe
        String validationError = validateFields(email, password);

        if (validationError != null) {
            showAlert("Error", validationError);
            return;  // Salir del método si hay errores
        }

        // Comprobar credenciales (email y contraseña correctos)
        if (email.equals("usuario@gmail.com") && password.equals("Abcd*1234")) {
            SignUpSignIn.navegarVentanas("MainDashboardFXML");
        } else {
            showAlert("Error", "Email o contraseña incorrectos.");
        }

    }

    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setOnCloseRequest(this::handleClose);
        });
    }

    private void handleClose(WindowEvent event) {
        event.consume();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.close();
        }
    }

    private String validateFields(String email, String password) {
        StringBuilder errorMessage = new StringBuilder();
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.append("El email o la contraseña no pueden estar vacíos.\n");
        } else if (!checkEmail(email)) {
            errorMessage.append("Formato de email inválido.\n");
        } else if (!checkPassword(password)) {
            errorMessage.append("La contraseña debe tener al menos 6 caracteres, con al menos una mayúscula, una minúscula y un número.\n");
        }
        return errorMessage.length() > 0 ? errorMessage.toString() : null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}