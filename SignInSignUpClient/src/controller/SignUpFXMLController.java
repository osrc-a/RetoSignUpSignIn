/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            // LocalDate fecha=fechaNac.getValue(); 
             //System.out.println(fecha.toString());

            Usuario usu = new Usuario();
            usu.setEmail(tfEmail.getText());
            if (!tfpContrasena.getText().equalsIgnoreCase(tfpContrasena2.getText())) {
                  usu.setContrasena(tfpContrasena.getText());
            } else {
              
            }

            usu.setNombre(tfNombre.getText());
            usu.setApellido(tfApellido.getText());

            FactorySignableClient.getSignable().registrar(usu);
        } catch (Exception ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
    }

}
    
