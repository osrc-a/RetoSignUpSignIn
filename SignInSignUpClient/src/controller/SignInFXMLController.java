/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * FXML Controller class
 *
 * @author 2dam
 */
package controller;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import userinterfacetier.SignUpSignIn;

public class SignInFXMLController {
    
    

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPsswd;

    // Expresión regular para validar email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Expresión regular para validar contraseña
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";

    @FXML
    private void handleLogin() throws Exception {
        String email = txtEmail.getText();
        String password = txtPsswd.getText();

        // Validar los campos y obtener el mensaje de error, si existe
        String validationError = validarCampos(email, password);

        if (validationError != null) {
            mostrarAlerta("Error", validationError);
            return;  // Salir del método si hay errores
        }

        // Comprobar credenciales (email y contraseña correctos)
        if (email.equals("usuario") && password.equals("contraseña")) {
            SignUpSignIn.navegarVentanas("MainDashboardFXML.fxml");
        } else {
            mostrarAlerta("Error", "Email o contraseña incorrectos.");
        }
    }

    // Validar los campos y devolver los mensajes de error si los hay
    private String validarCampos(String email, String password) {
        StringBuilder errorMessage = new StringBuilder();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.append("El email o la contraseña no pueden estar vacíos.\n");
        }

        if (!comprobarEmail(email)) {
            errorMessage.append("Formato de email inválido.\n");
        }

        if (!comprobarPassword(password)) {
            errorMessage.append("La contraseña debe tener al menos 6 caracteres, con al menos una mayúscula, una minúscula y un número.\n");
        }

        // Si no hay errores, devuelve null
        return errorMessage.length() > 0 ? errorMessage.toString() : null;
    }

    // Mostrar alerta con el mensaje dado
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para comprobar el formato del email
    private boolean comprobarEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Método para comprobar el formato de la contraseña
    private boolean comprobarPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Navegar a la ventana de registro (Sign Up)
    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }
}