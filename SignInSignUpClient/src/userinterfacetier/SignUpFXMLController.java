/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterfacetier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class SignUpFXMLController{
    
    @FXML
    private TextField nombre;
    
    @FXML
    private TextField apellido;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField contraseina;
    
    @FXML
    private TextField edad;
    
    
    
    private void registro(ActionEvent event){
        Usuario usu=new Usuario();
        usu.setNombre(nombre.getText());
        usu.setApellido(apellido.getText());
        usu.setEmail(email.getText());
        
        
        
    }

}
