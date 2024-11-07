/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import modelo.ActionUsers;
import modelo.FactorySignableClient;
import modelo.Usuario;
import userinterfacetier.SignUpSignIn;
import utils.Actions;
import utils.Errores;

public class SignInFXMLController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPsswd;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

    @FXML
    private void handleLogin() {

        try {
            ActionUsers userr = new ActionUsers();
            Usuario user = new Usuario();
            String email = txtEmail.getText();
            String password = txtPsswd.getText();
            String errorMessage = validateFields(email, password);
            if (errorMessage != null) {
                showAlert("Error", errorMessage);
                return;
            }
            user.setEmail(email);
            user.setContrasena(password);
            userr.setAction(Actions.LOGGING_REQUEST);
            userr.setUser(user);
            userr = FactorySignableClient.getSignable().login(userr);
            if (userr.getUser().getActivo()) {
                SignUpSignIn.navegarVentanas("MainDashboardFXML.fxml");
                
            } else {
                new Alert(Alert.AlertType.INFORMATION, "El usuario no esta activo").showAndWait();
            }

        } catch (Errores.DatabaseConnectionException | Errores.UserAlreadyExistsException | Errores.ServerConnectionException | Errores.AuthenticationFailedException | Errores.PropertiesFileException ex) {
            Logger.getLogger(SignInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(SignInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }

    public void initialize() {
        // Se usa Platform.runLater() para asegurarse de que el Stage esté inicializado
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) txtEmail.getScene().getWindow();
                // Configuramos el evento al cerrar la ventana con la "X"
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        event.consume();  // Consumir el evento para manejarlo manualmente
                        handleClose();
                    }
                });
            }
        });
    }

    private void handleClose() {
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
