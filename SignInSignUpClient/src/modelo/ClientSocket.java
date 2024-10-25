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

    public void login(Usuario user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


