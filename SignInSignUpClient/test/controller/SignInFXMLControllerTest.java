/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import userinterfacetier.SignUpSignIn;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInFXMLControllerTest extends ApplicationTest {

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void testSigInCorrect() {
        clickOn("#txtEmail");
        write("usuario@gmail.com");
        clickOn("#txtPsswd");
        write("Abcd*1234");
        clickOn("#btnEntrar");
        verifyThat("#lbBienvenido", isVisible());
    }

    @Test
    public void testNavegacionEntreVentanas() {
        clickOn("#btnCrearCuenta");
        write("usuario@gmail.com");
        clickOn("#txtPsswd");
        write("Abcd*1234");
        clickOn("#btnEntrar");
        verifyThat("#lbBienvenido", isVisible());
    }
}
