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

/**
 *
 * @author 2dam
 */
public class ClientSocket implements Signable {

    private final int PUERTO = 5000;
    private final String IP = "127.0.0.1";

    @Override
    public void registrar(Usuario user) {
        Socket socket = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;

        try {
            socket = new Socket(IP, PUERTO);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
            String mensaje = (String) entrada.readObject();
            System.out.println(mensaje);
            
            
            System.out.println("Mandando usuario");
            salida.writeObject(user);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
 public Usuario login(Usuario user) throws Exception {
        try (Socket socket = new Socket(IP, PUERTO);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            // Enviar solicitud de inicio de sesión con el objeto Usuario
            output.writeObject("LOGIN"); // Identificador de operación 
            output.writeObject(user); // Usuario con username y password

            // Recibir respuesta del servidor
            Object response = input.readObject();

            if (response instanceof Usuario) {
                Usuario loggedInUser = (Usuario) response;
                System.out.println("Login exitoso para usuario: " + loggedInUser.getNombre());
                return loggedInUser; // Retorna el usuario validado
            } else {
                System.out.println("Error en el inicio de sesión.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al intentar iniciar sesión: " + e.getMessage());
            throw e;
        }
}
    
}


