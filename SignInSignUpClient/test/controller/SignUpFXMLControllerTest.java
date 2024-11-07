/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import userinterfacetier.SignUpSignIn;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpFXMLControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void primerTest() {
        clickOn("#btnSignUp");

        clickOn("#btnCrearCuenta"); // Intenta registrarse sin rellenar campos
        verifyThat("Faltan campos por rellenar", isVisible());
        clickOn("#tfpContrasena");
        clickOn("Aceptar");

    }

    @Test
    public void testInvalidEmailFormat() {
        clickOn("#btnSignUp");
        clickOn("#tfEmail");
        write("invalidemail.com"); // Email inválido
        clickOn("#btnCrearCuenta");
        verifyThat("El email no tiene un formato correcto", isVisible());

    }
}
