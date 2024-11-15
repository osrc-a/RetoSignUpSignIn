package controller;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import userinterfacetier.SignUpSignIn;

public class SignInTestInicioSesion extends ApplicationTest {

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        // Registra el escenario principal para TestFX
        FxToolkit.registerPrimaryStage();
        // Configura la aplicación antes de las pruebas
        FxToolkit.setupApplication(SignUpSignIn.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void testLoginSuccessful() {
        // Simula la escritura de un correo electrónico y una contraseña válidos
        clickOn("#txtEmail").write("usuario@ejemplo.com");
        clickOn("#txtPsswd").write("Password123!");

        // Simula el clic en el botón de login
        clickOn("#btnEntar");

        // Verifica que el botón de cerrar sesión sea visible después del inicio de sesión exitoso
        verifyThat("#btnCerrarSesion", isVisible());  // Asegúrate de que el id sea el correcto
    }
}


