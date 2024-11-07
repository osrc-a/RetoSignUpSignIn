package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Usuario;
import userinterfacetier.ContextMenuManager;

public class MainDashboardFXMLController implements Initializable {

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private AnchorPane pane;

    @FXML
    private TreeTableView<String> treeTableView;

    @FXML
    private TreeTableColumn<String, String> columnaDatos;

    private ContextMenuManager contextMenuManager;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura la columna para mostrar los datos de cada TreeItem
        columnaDatos.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getValue()));
        

        // Inicializa el usuario con datos de prueba
        usuario = new Usuario();
        usuario.setNombre("Andoni");
        usuario.setApellido("Ordoñez");
        usuario.setEmail("andoni@example.com");
        usuario.setTelefono("123456789");
        usuario.setCalle("Calle Falsa 123");
        usuario.setCodigoPostal("48001");
        usuario.setCiudad("Bilbao");

        // Inicializar el ContextMenuManager
        contextMenuManager = new ContextMenuManager(pane);

        // Llama a setupTreeTableView para configurar los datos
        setupTreeTableView();

        // Configura el evento de cierre de ventana
        Platform.runLater(() -> {
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();
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
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.close();
        }
    }

    private void setupTreeTableView() {
        // Nodo raíz
        TreeItem<String> rootItem = new TreeItem<>("Datos del Usuario");

        // Añadiendo datos directamente en el TreeItem
        rootItem.getChildren().add(new TreeItem<>("Nombre: " + usuario.getNombre()));
        rootItem.getChildren().add(new TreeItem<>("Apellido: " + usuario.getApellido()));

        // Dirección con subelementos
        TreeItem<String> direccionItem = new TreeItem<>("Dirección");
        direccionItem.getChildren().add(new TreeItem<>("Calle: " + usuario.getCalle()));
        direccionItem.getChildren().add(new TreeItem<>("Código Postal: " + usuario.getCodigoPostal()));
        direccionItem.getChildren().add(new TreeItem<>("Ciudad: " + usuario.getCiudad()));
        rootItem.getChildren().add(direccionItem);

        // Contacto con subelementos
        TreeItem<String> contactoItem = new TreeItem<>("Contacto");
        contactoItem.getChildren().add(new TreeItem<>("Email: " + usuario.getEmail()));
        contactoItem.getChildren().add(new TreeItem<>("Teléfono: " + usuario.getTelefono()));
        rootItem.getChildren().add(contactoItem);

        // Configuramos el TreeTableView
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);
    }
}