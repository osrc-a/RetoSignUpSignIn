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
import utils.Errores.AuthenticationFailedException;
import utils.Errores.DatabaseConnectionException;
import utils.Errores.PropertiesFileException;
import utils.Errores.ServerConnectionException;
import utils.Errores.UserAlreadyExistsException;

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

    
    public ActionUsers conexion(ActionUsers user) throws DatabaseConnectionException, UserAlreadyExistsException, ServerConnectionException, AuthenticationFailedException, PropertiesFileException {
       
        try {
            iniciarConexion();
            
            salida.writeObject(user);
            user = (ActionUsers) entrada.readObject();
            switch (user.getAction()){
                case LOGGING_OK:
                    return user;
                case REGISTER_OK:
                    return user;
                case DATABASE_FAILED:
                    throw new Errores.DatabaseConnectionException("Error con la base de datos");
                case RESGISTER_FAILED:
                    throw new Errores.UserAlreadyExistsException("El usuario ya existe");
                case LOGGING_FAILED:
                    throw new Errores.AuthenticationFailedException("No existe usuario con esas credenciales");
                case SERVER_FAILED:
                    throw new Errores.ServerConnectionException("Fallo al abrir el servidor.");
                case PROPERTIESFILE_FAILED:
                    throw new Errores.ServerConnectionException("IP, URL, o CONTRASEÑA INVALIDA, cambia el archivo de propiedades.");
            }
            
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error en registro", e);
        } finally {
            cerrarConexion();
        }
        return user;
    }

    @Override
    public ActionUsers registrar(ActionUsers user) throws DatabaseConnectionException, UserAlreadyExistsException, ServerConnectionException, AuthenticationFailedException, PropertiesFileException {
            return conexion(user);
 
    }

    @Override
    public ActionUsers login(ActionUsers user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   
}
