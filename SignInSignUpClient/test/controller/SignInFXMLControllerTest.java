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
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import userinterfacetier.SignUpSignIn;


/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInFXMLControllerTest extends ApplicationTest {

    public void Start(Stage stage) throws Exception {

        new SignUpSignIn().start(stage);

    }

    public SignInFXMLControllerTest() {
    }

    @Test
    public void testSomeMethod() {
    }
    
    @Test
    public void test_UsuarioPasswd(){
        clickOn("#txtEmail");
        write("oscardan@gmx.es");
        clickOn("#txtPsswd");
        write("abcd*1234");
        clickOn("#btnEntrar");
        
        verifyThat("#pane",isVisible());
    }

}
