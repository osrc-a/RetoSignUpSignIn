/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2dam
 */
package userinterfacetier;

import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ContextMenuManager {

    private final ContextMenu contextMenu;

    public ContextMenuManager(AnchorPane mainPane) {
        contextMenu = new ContextMenu();

        // Elementos del menú contextual
        MenuItem viewProfile = new MenuItem("Ver Perfil");
        MenuItem changePassword = new MenuItem("Cambiar Contraseña");
        MenuItem settings = new MenuItem("Configuraciones");
        MenuItem logout = new MenuItem("Cerrar Sesión");
        MenuItem exit = new MenuItem("Salir");

        // Añadir los items al menú
        contextMenu.getItems().addAll(viewProfile, changePassword, settings, logout, exit);

        // Configurar acciones para cada opción del menú
        viewProfile.setOnAction(event -> showUserProfile());
        changePassword.setOnAction(event -> showChangePasswordAlert());
        settings.setOnAction(event -> showSettingsAlert());
        logout.setOnAction(event -> {
            try {
                handleLogout();
            } catch (Exception ex) {
                Logger.getLogger(ContextMenuManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        exit.setOnAction(event -> exitApplication());

        // Detectar clic derecho en el AnchorPane para mostrar el menú contextual
        mainPane.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(mainPane, event.getScreenX(), event.getScreenY());
            }
        });
    }

    // Métodos para mostrar alertas y manejar acciones
    private void showUserProfile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Perfil de Usuario");
        alert.setHeaderText("Información del Usuario");
        alert.setContentText("Nombre de usuario: Juan Pérez\nCorreo: jperez@example.com");
        alert.showAndWait();
    }

    private void showChangePasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cambiar Contraseña");
        alert.setHeaderText(null);
        alert.setContentText("Funcionalidad para cambiar la contraseña no implementada aún.");
        alert.showAndWait();
    }

    private void showSettingsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configuraciones");
        alert.setHeaderText(null);
        alert.setContentText("Configuraciones de la aplicación.");
        alert.showAndWait();
    }

    private void handleLogout() throws Exception {
        // Crear un Alert de tipo CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea cerrar sesión?");

        // Mostrar el Alert y esperar la respuesta
        Optional<ButtonType> result = alert.showAndWait();

        // Comprobar si se presionó "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SignUpSignIn.navegarVentanas("SignInFXML.fxml");
        }
    }

   private void exitApplication() {
        // Implementación para cerrar la aplicación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) contextMenu.getOwnerWindow();
            stage.close();
        }
   }
}
