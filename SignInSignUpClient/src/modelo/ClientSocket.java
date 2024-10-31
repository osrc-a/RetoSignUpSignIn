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
import utils.Actions;
import utils.Errores;

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
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error al cerrar conexión", e);
        }
    }

    @Override
    public ActionUsers registrar(ActionUsers user) throws Errores.DatabaseConnectionException {
        ActionUsers mensaje = null;
        try {
            iniciarConexion();
            mensaje = (ActionUsers) entrada.readObject();
            salida.writeObject(user);
            if(mensaje.getAction().equals(Actions.DATABASE_FAILED)) {
                throw new Errores.DatabaseConnectionException("");
            }
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error en registro", e);
        } finally {
            cerrarConexion();
        }
        return mensaje;
    }

    @Override
    public ActionUsers login(Usuario user) throws Exception {
        return null;
    }
}
