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

public class SignInComprobarCampos extends ApplicationTest {

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
    public void testFieldsAreEmptyOnStart() {
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#txtPsswd", hasText(""));
    }

    @Test
    public void testErrorAppearsOnInvalidEmail() {
        // Escribe un email inválido y una contraseña corta
        clickOn("#txtEmail").write("correo@invalido");
        clickOn("#txtPsswd").write("12345AAA");

        // Hacer clic en el botón de login
        clickOn("#btnEntrar");

        // Verifica que el mensaje de error es visible
        verifyThat(".alert", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void testErrorAppearsOnInvalidPasswd() {
        // Escribe un email inválido y una contraseña corta
        clickOn("#txtEmail").write("correo@dominio.com");
        clickOn("#txtPsswd").write("12345");

        // Hacer clic en el botón de login
        clickOn("#btnEntrar");

        // Verifica que el mensaje de error es visible
        verifyThat(".alert", isVisible());
        clickOn("Aceptar");
    }

}












