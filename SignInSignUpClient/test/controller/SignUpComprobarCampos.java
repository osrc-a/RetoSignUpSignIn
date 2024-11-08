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
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import userinterfacetier.SignUpSignIn;

/**
 *
 * @author Markel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpComprobarCampos extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpSignIn().start(stage);
    }

    @Test
    public void SinCampos() {
        clickOn("#btnSignUp");

        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("Faltan campos por rellenar", isVisible());
        clickOn("Aceptar");

    }

    @Test
    public void EmailInvalido() {
        clickOn("#btnSignUp");
        clickOn("#tfEmail");
        write("abcdefgmail.com");
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
        write("652112444");
        clickOn("#chActivo");

        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("El email no tiene un formato correcto", isVisible());
        clickOn("Aceptar");

    }

    @Test
    public void checboxDesactivado() {
        clickOn("#btnSignUp");
        clickOn("#tfEmail");
        write("abcdef@gmail.com");
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
        write("652112444");

        clickOn("#btnCrearCuenta");
        sleep(2000);
        verifyThat("Si dejas la casilla activo sin marcar no podras iniciar sesion", isVisible());
        clickOn("Cancelar");
    }

}
