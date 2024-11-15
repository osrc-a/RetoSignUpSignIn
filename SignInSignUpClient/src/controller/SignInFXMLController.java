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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import modelo.ActionUsers;
import modelo.FactorySignableClient;
import modelo.Usuario;
import userinterfacetier.SignUpSignIn;
import utils.Actions;
import utils.Errores;

/**
 * Controlador para la ventana de inicio de sesión (Sign In). Este controlador
 * maneja los eventos y validaciones de los campos de correo y contraseña y el
 * proceso de autenticación.
 *
 * @version 1.0
 *
 *
 * @author Oscar
 * @author Andoni
 *
 *
 */
public class SignInFXMLController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPsswd;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

    /**
     * Maneja el proceso de inicio de sesión. Verifica que los campos de correo
     * y contraseña cumplan con los requisitos y envía las credenciales al
     * servidor para autenticación.
     */
    @FXML
    private void handleLogin() {
        try {
            ActionUsers userr = new ActionUsers();
            Usuario user = new Usuario();
            String email = txtEmail.getText();
            String password = txtPsswd.getText();
            user.setEmail(email);
            user.setContrasena(password);
            userr.setAction(Actions.LOGGING_REQUEST);
            
            userr.setUser(user);
            userr = FactorySignableClient.getSignable().login(userr);
            
            if (userr.getUser().getActivo()) {
                SignUpSignIn.navegarVentanas("MainDashboardFXML.fxml");
            } else {
                new Alert(Alert.AlertType.INFORMATION, "El usuario no está activo").showAndWait();
            }

        } catch (Errores.DatabaseConnectionException | Errores.UserAlreadyExistsException | Errores.ServerConnectionException | Errores.PropertiesFileException ex) {
            Logger.getLogger(SignInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch (Errores.AuthenticationFailedException ex) {
            Logger.getLogger(SignInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            txtEmail.setStyle("-fx-text-inner-color: red");
            txtPsswd.setStyle("-fx-text-inner-color: red");
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(SignInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isStringValidPhoneNumber(String field) {
        // do the validating here
        return false;
    }

    /**
     * Navega a la ventana de registro.
     *
     * @throws Exception Si ocurre un error en la navegación.
     */
    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }
    @FXML
    private Button btnShowPassword;

    private boolean isPasswordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Cambia a campo de contraseña
            txtPsswd.setText(txtPsswd.getText());
            txtPsswd.setPromptText("Ingresa tu contraseña");
            isPasswordVisible = false;
            btnShowPassword.setText("👁");
        } else {
            // Cambia a campo de texto
            txtPsswd.setPromptText(txtPsswd.getText());
            txtPsswd.clear();
            isPasswordVisible = true;
            btnShowPassword.setText("👁");
        }
    }

    /**
     * Inicializa el controlador y configura el evento de cierre de la ventana.
     * Este método se asegura de que el Stage esté listo antes de configurar el
     * manejador del evento de cierre.
     */
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    handleClose();
                }
            });
        });
    }

    /**
     * Maneja el cierre de la aplicación mostrando una alerta de confirmación.
     */
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

    /**
     * Muestra una alerta con el mensaje de error.
     *
     * @param title Título de la alerta.
     * @param message Mensaje que se mostrará en la alerta.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Comprueba si el correo electrónico cumple con el formato correcto.
     *
     * @param email El correo a validar.
     * @return true si el correo tiene un formato válido, false en caso
     * contrario.
     */
    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Comprueba si la contraseña cumple con el formato correcto.
     *
     * @param password La contraseña a validar.
     * @return true si la contraseña cumple con los requisitos, false en caso
     * contrario.
     */
    private boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

