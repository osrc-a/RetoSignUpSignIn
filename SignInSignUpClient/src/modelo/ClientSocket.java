/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.UserAction;

public class ClientSocket implements Signable {
    private final int PUERTO = 5000;
    private final String IP = "127.0.0.1";
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    private void iniciarConexion() throws IOException {
        socket = new Socket(IP, PUERTO);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    private void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error al cerrar conexión", e);
        }
    }

    @Override
    public void registrar(Usuario user) {
        try {
            iniciarConexion();
            salida.writeObject(UserAction.REGISTER_REQUEST); // Envía un comando estandarizado
            salida.writeObject(user);
            String mensaje = (String) entrada.readObject();
            System.out.println(mensaje);
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error en registro", e);
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public Usuario login(Usuario user) throws Exception {
        try {
            iniciarConexion();
            salida.writeObject(UserAction.LOGIN_REQUEST); // Enviar comando de login
            salida.writeObject(user);

            Object response = entrada.readObject();
            if (response instanceof Usuario) {
                Usuario loggedInUser = (Usuario) response;
                System.out.println("Login exitoso para usuario: " + loggedInUser.getNombre());
                return loggedInUser;
            } else {
                System.out.println("Error en el inicio de sesión.");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error al intentar iniciar sesión", e);
            throw e;
        } finally {
            cerrarConexion();
        }
    }
}
