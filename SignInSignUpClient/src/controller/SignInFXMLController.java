package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;
import userinterfacetier.SignUpSignIn;

public class SignInFXMLController {

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

    @FXML
    private void irASignUp() throws Exception {
        SignUpSignIn.navegarVentanas("SignUpFXML.fxml");
    }

    public void initialize() {
        // Se usa Platform.runLater() para asegurarse de que el Stage esté inicializado
        Platform.runLater(() -> {
            Stage stage = (Stage) lblError.getScene().getWindow();
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
            Stage stage = (Stage) lblError.getScene().getWindow();
            stage.close();
        }
    }
}
