/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.ActionUsers;
import modelo.FactorySignableClient;
import modelo.Usuario;
import userinterfacetier.SignUpSignIn;
import utils.Actions;
import utils.Errores;

/**
 *
 * @author 2dam
 */
public class SignUpFXMLController {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private PasswordField tfpContrasena;
    @FXML
    private PasswordField tfpContrasena2;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfTelefono;
    @FXML
    private CheckBox chActivo;

    @FXML
    private void registro(ActionEvent event) throws  ExceptionInInitializerError{
        try {

            if (comprobarDatos()) {
                Usuario user = insercionDeDatos();
                ActionUsers userr = new ActionUsers();

                userr.setAction(Actions.REGISTER_REQUEST);
                userr.setUser(user);
                if (chActivo.isSelected() || mostrarAlert("Cuidado", "Si dejas la casilla activo sin marcar no podras iniciar sesion")) {
                    FactorySignableClient.getSignable().registrar(userr);
                    new Alert(Alert.AlertType.INFORMATION, "Registro existoso").showAndWait();
                    clearFields();

                }

            }
        } catch (Errores.DatabaseConnectionException | Errores.UserAlreadyExistsException | Errores.ServerConnectionException | Errores.PropertiesFileException | Errores.AuthenticationFailedException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();

        }
    }

    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        // Expresión regular para validar la contraseña
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        try {
            // Intentar convertir el texto a un número entero
            Integer.parseInt(phoneNumber);
            return true; // La conversión fue exitosa, es un entero
        } catch (NumberFormatException e) {
            return false; // La conversión falló, no es un entero
        }
    }

    private boolean mostrarAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private Usuario insercionDeDatos() {
        Usuario usu = new Usuario();

        usu.setEmail(tfEmail.getText());
        usu.setContrasena(tfpContrasena.getText());
        usu.setNombre(tfNombre.getText());
        usu.setApellido(tfApellido.getText());
        usu.setCalle(tfCalle.getText());
        usu.setCodigoPostal(tfCodigoPostal.getText());
        usu.setTelefono(tfTelefono.getText());
        usu.setCiudad(tfCiudad.getText());
        usu.setActivo(chActivo.isSelected());

        return usu;
    }

    private void clearFields() {
        // Clear all input fields after successful registration
        tfEmail.clear();
        tfpContrasena.clear();
        tfpContrasena2.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfCalle.clear();
        tfCodigoPostal.clear();
        tfCiudad.clear();
        tfTelefono.clear();
        chActivo.setSelected(false);
    }

    private boolean comprobarDatos() {
        if (tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfCalle.getText().isEmpty() || tfCodigoPostal.getText().isEmpty() || tfCiudad.getText().isEmpty() || tfTelefono.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Faltan campos por rellenar").showAndWait();
            return false;
        }
        if (!isValidEmail(tfEmail.getText())) {
            new Alert(Alert.AlertType.ERROR, "El email no tiene un formato correcto").showAndWait();
            return false;
        }

        if (!isValidPassword(tfpContrasena.getText()) || !isValidPassword(tfpContrasena2.getText())) {
            if (!tfpContrasena.getText().equals(tfpContrasena2.getText())) {
                new Alert(Alert.AlertType.ERROR, "Las contraseñas no coinciden").showAndWait();
                return false;
            }
            new Alert(Alert.AlertType.ERROR, "La contraseña tiene que ser de ocho caracteres, usa al menos una minuscula, una mayuscula y un digito").showAndWait();
            return false;
        }

        if (!isValidPhoneNumber(tfTelefono.getText())) {
            new Alert(Alert.AlertType.ERROR, "El numero de telefono no es valido").showAndWait();
            return false;
        }

        return true;
    }

    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) tfApellido.getScene().getWindow();
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

        if (mostrarAlert("Confirmacion", "¿Está seguro de que desea cerrar la aplicación? Todos los cambios no guardados se perderán.")) {
            Stage stage = (Stage) tfApellido.getScene().getWindow();
            stage.close();
        }
    }

}
