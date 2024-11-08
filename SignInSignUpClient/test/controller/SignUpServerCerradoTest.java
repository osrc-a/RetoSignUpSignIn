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
public class SignUpServerCerradoTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void ServidorCerrado() {
        clickOn("#btnSignUp");

        clickOn("#tfEmail");
        write("markela@gmail.com");
        clickOn("#tfpContrasena");
        write("Abcd*1234");
        clickOn("#tfpContrasena2");
        write("Abcd*1234");
        clickOn("#tfNombre");
        write("Markel");
        clickOn("#tfApellido");
        write("arabio");
        clickOn("#tfCalle");
        write("lehendakari aguirre");
        clickOn("#tfCodigoPostal");
        write("4214");
        clickOn("#tfCiudad");
        write("Bilbao");
        clickOn("#tfTelefono");
        write("42112444");
        clickOn("#chActivo");
        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("El servidor no esta abierto", isVisible());
        clickOn("Aceptar");

    }

   

}
/* clickOn("#btnCrearCuenta"); // Intenta registrarse sin rellenar campos
        verifyThat("Faltan campos por rellenar", isVisible());*/
