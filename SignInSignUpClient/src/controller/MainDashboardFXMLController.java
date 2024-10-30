/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterfacetier.ContextMenuManager;

public class MainDashboardFXMLController implements Initializable {


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    

    @FXML
    private Label lbBienvenido;

    @FXML
    private AnchorPane pane; // Asegúrate de que este es el ID de tu AnchorPane en el FXML.

    private ContextMenuManager contextMenuManager;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Verifica que los elementos @FXML no sean nulos
    
        // Inicializar el ContextMenuManager
        contextMenuManager = new ContextMenuManager(pane);

        // Configurar el evento al cerrar la ventana
        Platform.runLater(() -> {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();  // Consumir el evento para manejarlo manualmente
                handleClose();
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
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            stage.close();
        }
    }

}
