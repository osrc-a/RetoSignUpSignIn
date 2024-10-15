/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Signable;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class ClientSocket implements Signable {

private final int PUERTO = 9000;
private final String IP = "127.0.0.1";

@Override
public void registrar(Usuario user) {
    Socket socket = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;

    try {
        socket = new Socket(IP, PUERTO);

        System.out.println("Esperando que el servidor envíe algo....");
        entrada = new ObjectInputStream(socket.getInputStream());
        salida = new ObjectOutputStream(socket.getOutputStream());

       
        salida.writeObject(user);
        String mensaje = (String) entrada.readObject();


    } catch (IOException ex) {
        Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
    }

}



}
