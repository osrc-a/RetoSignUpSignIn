/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import errores.ValidationError;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private void registro(ActionEvent event) {
        try {
            Usuario user = insercionDeDatos();
            ActionUsers userr = new ActionUsers();
            userr.setAction(Actions.REGISTER_REQUEST);
            userr.setUser(user);
            FactorySignableClient.getSignable().registrar(userr);
            mostrarAlert("Éxito", "Registro exitoso."); // Show success message
            clearFields(); // Clear fields after successful registration
        } catch (ValidationError ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
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
    }
 }
                          
    private void handleClose(WindowEvent event) {
        event.consume();  // Consume the event to handle manually
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.close();
        }
    }

    private String validarCampos(String email, String contrasena, String contrasena2,
                                 String nombre, String apellido, String calle,
                                 String codigoPostal, String ciudad, String telefono,
                                 Boolean activo) {
        StringBuilder errorMessage = new StringBuilder();

        if (!activo) {
            errorMessage.append("Si te registras como no activo, entonces no podrás iniciar sesión.\n");
        }
        if (!contrasena.equals(contrasena2)) {
            errorMessage.append("Las contraseñas no coinciden.\n");
        }
        if (codigoPostal == null || codigoPostal.isEmpty()) {
            errorMessage.append("Código postal no válido.\n");
        } else {
            try {
                Integer.parseInt(codigoPostal);
            } catch (NumberFormatException e) {
                errorMessage.append("Código postal no válido (Deben ser números).\n");
            }
        }
        if (!isValidEmail(email)) {
            errorMessage.append("Formato de email no válido.\n");
        }

        return errorMessage.length() > 0 ? errorMessage.toString() : null;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private void mostrarAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Usuario insercionDeDatos() throws ValidationError {
        Usuario usu = new Usuario();
        String validationError = validarCampos(tfEmail.getText(),
                tfpContrasena.getText(), tfpContrasena2.getText(),
                tfNombre.getText(), tfApellido.getText(), tfCalle.getText(),
                tfCodigoPostal.getText(), tfCiudad.getText(),
                tfTelefono.getText(), chActivo.isSelected());

        if (validationError != null) {
            mostrarAlert("Error", validationError);
            throw new ValidationError("Los campos introducidos tienen un formato incorrecto.");
        }

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
}