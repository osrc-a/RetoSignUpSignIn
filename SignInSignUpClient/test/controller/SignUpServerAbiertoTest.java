/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import userinterfacetier.SignUpSignIn;

/**
 *
 * @author Markel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpServerAbiertoTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void ServidorAbierto() {
        clickOn("#btnSignUp");

        clickOn("#tfEmail");
        write("markel@gmail.com");
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
        write("42514");
        clickOn("#tfCiudad");
        write("Bilbao");
        clickOn("#tfTelefono");
        write("621128444");
        clickOn("#chActivo");
        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("Registro existoso", isVisible());
        clickOn("Aceptar");

    }

    @Test
    public void ServidorAbiertoUsuarioRepetido() {
        clickOn("#btnSignUp");

        clickOn("#tfEmail");
        write("markel@gmail.com");
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
        write("42514");
        clickOn("#tfCiudad");
        write("Bilbao");
        clickOn("#tfTelefono");
        write("621128444");
        clickOn("#chActivo");
        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("El usuario ya existe", isVisible());
        clickOn("Aceptar");

    }

}
