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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Errores;
import utils.Errores.AuthenticationFailedException;
import utils.Errores.DatabaseConnectionException;
import utils.Errores.PropertiesFileException;
import utils.Errores.ServerConnectionException;
import utils.Errores.UserAlreadyExistsException;

/**
 * Clase que maneja la conexión con el servidor mediante un socket. Esta clase
 * implementa la interfaz Signable para gestionar las acciones de login y
 * registro de un usuario. Realiza las peticiones al servidor y maneja las
 * respuestas correspondientes.
 *
 * @author Oscar
 * @author Markel
 */
public class ClientSocket implements Signable {

    // Carga el archivo de configuración con los parámetros de conexión
    ResourceBundle fichConfig = ResourceBundle.getBundle("propiedades.connectionWithServer");
    // Obtiene el puerto y la IP del servidor desde el archivo de propiedades
    private final int PUERTO = Integer.valueOf(fichConfig.getString("PUERTO"));
    private final String IP = fichConfig.getString("IP");
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    /**
     * Inicia la conexión con el servidor a través de un socket. Abre los flujos
     * de entrada y salida para poder enviar y recibir datos.
     *
     * @throws IOException si ocurre un error al abrir los flujos de entrada o
     * salida
     */
    private void iniciarConexion() throws IOException {
        socket = new Socket(IP, PUERTO); // Establece la conexión con el servidor
        salida = new ObjectOutputStream(socket.getOutputStream()); // Flujo de salida
        entrada = new ObjectInputStream(socket.getInputStream()); // Flujo de entrada
    }

    /**
     * Cierra la conexión con el servidor. Cierra los flujos de entrada y
     * salida, y el socket.
     */
    private void cerrarConexion() {
        try {
            if (entrada != null) {
                entrada.close(); // Cierra el flujo de entrada
            }
            if (salida != null) {
                salida.close(); // Cierra el flujo de salida
            }
            if (socket != null) {
                socket.close(); // Cierra el socket
            }
        } catch (IOException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Error al cerrar conexión", e);
        }
    }

    /**
     * Realiza la conexión con el servidor y maneja las respuestas recibidas.
     * Dependiendo de la respuesta del servidor, lanza las excepciones
     * correspondientes o retorna el objeto de usuario.
     *
     * @param user el objeto ActionUsers que contiene los datos del usuario para
     * login o registro
     * @return el objeto ActionUsers con la respuesta del servidor
     * @throws DatabaseConnectionException si ocurre un error con la base de
     * datos
     * @throws UserAlreadyExistsException si el usuario ya existe en la base de
     * datos
     * @throws ServerConnectionException si ocurre un error al conectar con el
     * servidor
     * @throws AuthenticationFailedException si las credenciales del usuario son
     * incorrectas
     * @throws PropertiesFileException si hay un problema con el archivo de
     * propiedades de configuración
     */
    public ActionUsers conexion(ActionUsers user) throws DatabaseConnectionException, UserAlreadyExistsException, ServerConnectionException, AuthenticationFailedException, PropertiesFileException {

        try {
            iniciarConexion(); // Inicia la conexión con el servidor

            salida.writeObject(user); // Envía el objeto ActionUsers al servidor
            user = (ActionUsers) entrada.readObject(); // Recibe la respuesta del servidor

            // Procesa la respuesta del servidor dependiendo del tipo de acción
            switch (user.getAction()) {
                case LOGGING_OK:
                    return user; // Login exitoso
                case REGISTER_OK:
                    return user; // Registro exitoso
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
            cerrarConexion(); // Asegura que la conexión se cierra después de la operación
        }
        return user;
    }

    /**
     * Realiza el registro de un nuevo usuario en el servidor.
     *
     * @param user el objeto ActionUsers que contiene los datos del usuario a
     * registrar
     * @return el objeto ActionUsers con la respuesta del servidor
     * @throws DatabaseConnectionException si ocurre un error con la base de
     * datos
     * @throws UserAlreadyExistsException si el usuario ya existe en la base de
     * datos
     * @throws ServerConnectionException si ocurre un error al conectar con el
     * servidor
     * @throws AuthenticationFailedException si las credenciales del usuario son
     * incorrectas
     * @throws PropertiesFileException si hay un problema con el archivo de
     * propiedades de configuración
     */
    @Override
    public ActionUsers registrar(ActionUsers user) throws DatabaseConnectionException, UserAlreadyExistsException, ServerConnectionException, AuthenticationFailedException, PropertiesFileException {
        return conexion(user); // Llama a la función de conexión para registrar al usuario
    }

    /**
     * Realiza el login de un usuario en el servidor.
     *
     * @param user el objeto ActionUsers que contiene las credenciales del
     * usuario
     * @return el objeto ActionUsers con la respuesta del servidor
     * @throws DatabaseConnectionException si ocurre un error con la base de
     * datos
     * @throws UserAlreadyExistsException si el usuario ya existe en la base de
     * datos
     * @throws ServerConnectionException si ocurre un error al conectar con el
     * servidor
     * @throws AuthenticationFailedException si las credenciales del usuario son
     * incorrectas
     * @throws PropertiesFileException si hay un problema con el archivo de
     * propiedades de configuración
     */
    @Override
    public ActionUsers login(ActionUsers user) throws DatabaseConnectionException, UserAlreadyExistsException, ServerConnectionException, AuthenticationFailedException, PropertiesFileException {
        return conexion(user); // Llama a la función de conexión para hacer login
    }

}
