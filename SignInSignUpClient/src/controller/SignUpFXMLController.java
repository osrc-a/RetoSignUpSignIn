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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.ActionUsers;
import modelo.FactorySignableClient;
import modelo.Usuario;
import userinterfacetier.SignUpSignIn;
import utils.Actions;
import utils.Errores;

/**
 * Controlador para la ventana de registro (Sign Up). Este controlador maneja
 * los eventos y validaciones de los campos de texto y los botones, y realiza el
 * proceso de registro de usuarios.
 *
 * @version 1.0
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

    /**
     * Método para registrar al usuario cuando se hace clic en el botón de
     * registro. Realiza la validación de los datos, inserta los datos en el
     * usuario y los envía al servidor.
     *
     * @param event El evento de acción que se activa al hacer clic en el botón.
     * @throws ExceptionInInitializerError Si ocurre un error en la
     * inicialización.
     *
     * @author Melany
     * @author Markel
     */
    @FXML
    private void mostrarContrasena1(ActionEvent event) {

        String a = tfpContrasena.getText();
        new Alert(Alert.AlertType.INFORMATION, "La contraseña del primer campo es -> " + a).showAndWait();

    }

    @FXML
    private void mostrarContrasena2(ActionEvent event) {

        String a = tfpContrasena2.getText();
        new Alert(Alert.AlertType.INFORMATION, "La contraseña del segundo campo es -> " + a).showAndWait();

    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void registro(ActionEvent event) throws ExceptionInInitializerError {

        try {
            if (comprobarDatos()) {
                Usuario user = insercionDeDatos();
                ActionUsers userr = new ActionUsers();
                userr.setAction(Actions.REGISTER_REQUEST);
                userr.setUser(user);

                if (chActivo.isSelected() || mostrarAlert("Cuidado", "Si dejas la casilla activo sin marcar no podras iniciar sesion")) {
                    userr = FactorySignableClient.getSignable().registrar(userr);
                    if (Actions.REGISTER_OK.equals(userr.getAction())) {
                        new Alert(Alert.AlertType.INFORMATION, "Registro existoso").showAndWait();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "El servidor no esta abierto").showAndWait();
                    }
                }
            }
        } catch (Errores.DatabaseConnectionException | Errores.UserAlreadyExistsException | Errores.ServerConnectionException | Errores.PropertiesFileException | Errores.AuthenticationFailedException ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    /**
     * Método para navegar a la ventana de inicio de sesión.
     *
     * @throws Exception Si ocurre un error en la navegación.
     */
    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
    }

    /**
     * Verifica si el email ingresado es válido.
     *
     * @param email Email que se va a verificar.
     * @return true si el email es válido, false en caso contrario.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    /**
     * Verifica si la contraseña ingresada cumple con los requisitos de
     * seguridad.
     *
     * @param password Contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    /**
     * Verifica si el número de teléfono ingresado es válido.
     *
     * @param phoneNumber Número de teléfono a validar.
     * @return true si el número es un número entero válido, false en caso
     * contrario.
     */
    private boolean isValidNumerico(String numerico) {
        try {
            Integer.parseInt(numerico);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Muestra una alerta de confirmación al usuario.
     *
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     * @return true si el usuario confirma, false en caso contrario.
     */
    private boolean mostrarAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * Inserta los datos de los campos en un objeto Usuario.
     *
     * @return Objeto Usuario con los datos ingresados.
     */
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

    /**
     * Limpia todos los campos del formulario.
     */
    private void clearFields() {
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

    /**
     * Comprueba que los datos del formulario sean válidos.
     *
     * @return true si los datos son válidos, false en caso contrario.
     */
    private boolean comprobarDatos() {

        if (tfEmail.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta Email").showAndWait();
            return false;
        } else if (tfNombre.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Falta Nombre").showAndWait();
            return false;
        } else if (tfApellido.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta Apellido").showAndWait();
            return false;
        } else if (tfCalle.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta Calle").showAndWait();
            return false;
        } else if (tfCodigoPostal.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta codigo postal").showAndWait();
            return false;
        } else if (tfCiudad.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta Ciudad").showAndWait();
            return false;

        } else if (tfTelefono.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Falta telefono").showAndWait();
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
            new Alert(Alert.AlertType.ERROR, "La contraseña debe tener ocho caracteres y al menos una minúscula, una mayúscula y un dígito").showAndWait();
            return false;
        }
        if (!isValidNumerico(tfTelefono.getText())) {
            new Alert(Alert.AlertType.ERROR, "El número de teléfono no es válido").showAndWait();
            return false;
        }
        if (!isValidNumerico(tfCodigoPostal.getText())) {
            new Alert(Alert.AlertType.ERROR, "El número de codigo postal no es válido").showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Inicializa el controlador y configura el evento de cierre de la ventana.
     */
    public void initialize() {

        tfEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (!isValidEmail(tfEmail.getText())) {
                        tfEmail.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }

                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });

        tfpContrasena.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (!isValidPassword(tfpContrasena.getText()) || !isValidPassword(tfpContrasena2.getText()) || !tfpContrasena.getText().equals(tfpContrasena2.getText())) {
                        tfpContrasena.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                        tfpContrasena2.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    } else {
                        tfpContrasena.setStyle("-fx-border-width: 0px ;");
                        tfpContrasena2.setStyle("-fx-border-width: 0px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfpContrasena2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (!isValidPassword(tfpContrasena.getText()) || !isValidPassword(tfpContrasena2.getText()) || !tfpContrasena.getText().equals(tfpContrasena2.getText())) {
                        tfpContrasena.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                        tfpContrasena2.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    } else {
                        tfpContrasena.setStyle("-fx-border-width: 0px ;");
                        tfpContrasena2.setStyle("-fx-border-width: 0px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfNombre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (tfNombre.getText().isEmpty()) {
                        tfNombre.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfApellido.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (tfApellido.getText().isEmpty()) {
                        tfApellido.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfCalle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (tfCalle.getText().isEmpty()) {
                        tfCalle.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfCodigoPostal.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (!isValidNumerico(tfCodigoPostal.getText())) {
                        tfCodigoPostal.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfCiudad.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (tfCiudad.getText().isEmpty()) {
                        tfCiudad.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });
        tfTelefono.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                //Controlamos cuando dimX pierde el foco
                if (!arg2) {
                    if (!isValidNumerico(tfTelefono.getText())) {
                        tfTelefono.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                    //Aquí va el código que queremos que se ejecute cuando dimX pierda el foco. 
                }
            }
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) tfApellido.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();
                handleClose();
            });
        });
    }

    /**
     * Maneja el evento de cierre de la aplicación, mostrando una confirmación.
     */
    private void handleClose() {
        if (mostrarAlert("Confirmacion", "¿Está seguro de que desea cerrar la aplicación? Todos los cambios no guardados se perderán.")) {
            Stage stage = (Stage) tfApellido.getScene().getWindow();
            stage.close();
        }
    }
}
