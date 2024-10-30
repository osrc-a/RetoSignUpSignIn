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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.FactorySignableClient;
import modelo.Usuario;
import userinterfacetier.SignUpSignIn;

/**
 *
 * @author 2dam
 */
public class SignUpFXMLController{

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
    private PasswordField tfCalle;
   @FXML
    private PasswordField tfCodigoPostal;
      @FXML
    private PasswordField tfCiudad;
         @FXML
    private PasswordField tfTelefono;
   @FXML
    private CheckBox chActivo;
  
    @FXML
    private void registro(ActionEvent event) throws Exception  {
       
         String email=tfEmail.getText();
         String contrasena=tfpContrasena.getText();
         String contrasena2 =tfpContrasena2.getText();
         String nombre=tfNombre.getText();
         String apellido= tfApellido.getText();
         String calle=tfCalle.getText();
         String codigoPostal=tfCodigoPostal.getText();
         String ciudad=tfCiudad.getText();
         String telefono=tfTelefono.getText();
         Boolean activo=chActivo.isSelected();
       
            String validationError = validarCampos(email, contrasena, contrasena2, nombre , apellido, calle, codigoPostal, ciudad, telefono, activo);
            if (validationError!= null) {
                mostrarAlert("Error", validationError);
              // Salir del método si hay errores
              }else{
                insercionDeDatos();
            }      
    }
    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
    }
    
    public void initialize() {
        // Se usa Platform.runLater() para asegurarse de que el Stage esté inicializado
        Platform.runLater(() -> {
            Stage stage = (Stage) tfApellido.getScene().getWindow();
            // Configuramos el evento al cerrar la ventana con la "X"
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();  // Consumir el evento para manejarlo manualmente
                    handleClose();
                }
            });
        });
    }

    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) tfApellido.getScene().getWindow();
            stage.close();
        }
    }

    private String validarCampos(String email, String contrasena, String contrasena2, String nombre, String apellido, String calle, String codigoPostal, String ciudad, String telefono, Boolean activo) {

            StringBuilder errorMessage = new StringBuilder();

    if (chActivo.isSelected()) {
        errorMessage.append( "Si te registras como no activo entonces no podras iniciar secion.");
        }else  if (!contrasena.equalsIgnoreCase(contrasena2)) {
        errorMessage.append("Las contraseñas no coinciden");
        }else   if (codigoPostal == null || codigoPostal.length() == 0) {
        errorMessage.append("Codigo postal no valido");
        }else{
     try { 
                    Integer.parseInt(codigoPostal);
                } catch (NumberFormatException e) {
                    errorMessage.append("Codigo postal no valido (Deben ser numeros)\n");
                }
            }
            return errorMessage.length() > 0 ? errorMessage.toString() : null;
            }
private void  mostrarAlert(String titulo, String mensaje) {
// Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
       }

    private void insercionDeDatos() {
        try{
           
          Usuario usu = new Usuario();
                    usu.setEmail(tfEmail.getText());
                        usu.setContrasena(tfpContrasena.getText()); 
                        usu.setNombre(tfNombre.getText());
                        usu.setApellido(tfApellido.getText());
                        usu.setCalle(tfCalle.getText());
                        usu.setCodigoPostal(tfCodigoPostal.getText());
                        usu.setCiudad(tfCiudad.getText());
                        usu.setTelefono(tfTelefono.getText());
                        usu.setActivo(chActivo.isSelected());
                        FactorySignableClient.getSignable().registrar(usu);
                } catch (Exception ex) {
                Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }


