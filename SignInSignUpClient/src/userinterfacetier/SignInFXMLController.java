/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterfacetier;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class SignInFXMLController {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPsswd;
    @FXML
    private Label lblError;
    @FXML
    
    private void handleLogin() throws Exception {
    String email = txtEmail.getText();
    String password = txtPsswd.getText();

    if (email.isEmpty() || password.isEmpty()) {
        lblError.setText("Por favor, completa todos los campos.");
        return;
    }

    if (email.equals("usuario") && password.equals("contraseña")) {
        SignUpSignIn.navegarVentanas("MainDashboardFXML.fxml");
    } else {
        lblError.setText("Correo o contraseña incorrectos");
    }
}
    // Navegar a la ventana de registro (Sign Up)
    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }
}
   
    

    

