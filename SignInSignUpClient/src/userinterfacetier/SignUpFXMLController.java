/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterfacetier;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.FactorySignable;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class SignUpFXMLController {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfApellido;

    @FXML
    private TextField tfEmail;

    
    @FXML
    private void registro(ActionEvent event) {

        try {
            System.out.println("userinterfacetier.SignUpFXMLController.registro()");
            Usuario usu = new Usuario();
            usu.setNombre(tfNombre.getText());
            usu.setApellido(tfApellido.getText());
            usu.setEmail(tfEmail.getText());
            FactorySignable.getSignable().registrar(usu);
        } catch (Exception ex) {
            Logger.getLogger(SignUpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void irASignIn() throws Exception {
        SignUpSignIn.navegarVentanas("SignInFXML.fxml");
    }

}