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
import javafx.scene.control.DatePicker;
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
    private DatePicker fechaNac;

    @FXML
    private void registro(ActionEvent event) {
     
        try {
                 Usuario usu = new Usuario();
           
             usu.setEmail(tfEmail.getText());
            if (tfpContrasena.getText().equalsIgnoreCase(tfpContrasena2.getText())) {
                  usu.setContrasena(tfpContrasena.getText());
            } else {
 
            } 

            usu.setNombre(tfNombre.getText());
            usu.setApellido(tfApellido.getText());
         //    LocalDate fecha=fechaNac.getValue(); 
           //  System.out.println(fecha.toString());

            FactorySignableClient.getSignable().registrar(usu);
            
            } catch (Exception ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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

}
