# RetoSignUpSignIn



Descripción del Proyecto:

Este proyecto ha sido desarrollado en Java utilizando la API de JavaFX, con el objetivo de proporcionar una interfaz gráfica de usuario (GUI) 
para gestionar el registro e inicio de sesión de usuarios. El sistema se conecta a una base de datos para realizar la inserción de nuevos usuarios
y la consulta de credenciales para el inicio de sesión.

Tanto el registro como el inicio de sesión incluyen una serie de validaciones que deben cumplirse, como la verificación de campos obligatorios, 
la validación de formatos y la comprobación de la coincidencia de datos introducidos, como contraseñas.

El proyecto soporta múltiples conexiones simultáneas de clientes, permitiendo que varios usuarios interactúen con el sistema de manera concurrente, 
sin interferir entre ellos. Esto se logra mediante la gestión eficiente de los recursos compartidos y el manejo adecuado de las conexiones.

El sistema está compuesto por tres programas principales:

Servidor: Encargado de gestionar las conexiones de los clientes, procesar las solicitudes y mantener la integridad de los datos en la base de datos.
Cliente: Interfaz que permite a los usuarios interactuar con el sistema, ya sea para registrarse o iniciar sesión.
Librería compartida: Un módulo común que contiene las funciones y clases utilizadas tanto por el servidor como por el cliente, facilitando la reutilización
del código y mejorando la estructura del proyecto.
