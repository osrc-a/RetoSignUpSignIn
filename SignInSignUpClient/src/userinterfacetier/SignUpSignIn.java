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
 *
 * @author 2dam
 */
public class SignUpSignIn extends javafx.application.Application {

    private Button button;

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("SignInFXML.fxml"));
        root.setId("pane");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        //stage.setWidth(800); 
        primaryStage.setResizable(false);
        //stage.setHeight(700); 
        primaryStage.getIcons().add(new Image("imagenes/logoSignUpSignIn.jpg"));

        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.show();

    }

    public static void navegarVentanas(String fmxl) throws Exception {

        Parent pane = FXMLLoader.load(SignUpSignIn.class.getResource(fmxl));
        primaryStage.getScene().setRoot(pane);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
