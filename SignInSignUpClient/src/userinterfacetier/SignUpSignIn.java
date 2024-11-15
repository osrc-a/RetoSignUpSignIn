/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterfacetier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase que maneja la interfaz de usuario para el inicio de sesión y el registro de usuarios.
 * La clase extiende `javafx.application.Application` y gestiona la visualización de las pantallas de inicio de sesión (SignIn) y registro (SignUp).
 * 
 * @author Oscar
 * @author Andoni
 */
public class SignUpSignIn extends javafx.application.Application {

    private Button button;
    private static Stage primaryStage; // Referencia estática a la ventana principal

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * Este método carga la interfaz de inicio de sesión (SignInFXML) y muestra la ventana principal.
     * 
     * @param stage La ventana principal que se pasa al iniciar la aplicación
     * @throws Exception Si ocurre algún error al cargar la interfaz FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("SignInFXML.fxml")); // Carga el archivo FXML para la pantalla de inicio de sesión
        root.setId("pane"); // Establece el ID del contenedor raíz
        Scene scene = new Scene(root); // Crea la escena con el contenido cargado
        primaryStage.setScene(scene); // Establece la escena en la ventana principal
        primaryStage.show(); // Muestra la ventana principal
        // Configura la ventana principal
        primaryStage.setResizable(false); // Hace que la ventana no sea redimensionable
        primaryStage.getIcons().add(new Image("resources/logoSignUpSignIn.jpg")); // Establece un ícono para la ventana
        stage.setTitle("Sign In"); // Establece el título de la ventana
        stage.show(); // Muestra la ventana
    }

    /**
     * Método estático que permite navegar entre diferentes pantallas FXML.
     * Cambia la raíz de la escena en la ventana principal según el archivo FXML proporcionado.
     * 
     * @param fmxl El nombre del archivo FXML que se desea cargar como nueva vista
     * @throws Exception Si ocurre un error al cargar el archivo FXML
     */
    public static void navegarVentanas(String fmxl) throws Exception {
        Parent pane = FXMLLoader.load(SignUpSignIn.class.getResource(fmxl)); // Carga el archivo FXML
        primaryStage.getScene().setRoot(pane); // Establece la nueva raíz para la escena
    }

    /**
     * Método principal de la aplicación.
     * Llama al método `launch` de la clase `Application` para iniciar la ejecución de la aplicación JavaFX.
     * 
     * @param args Los argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch(args); // Lanza la aplicación JavaFX
    }
}
