/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.*;
/**
 * Clase que maneja la conexión a la base de datos.
 * La conexión a la base de datos se establece directamente desde el código Java utilizando el controlador JDBC de MySQL.
 * @author Abel Camargo
 * @see <a href="https://www.youtube.com/watch?v=4wQy5uPTJLE">Referencia de conexión</a>
 */
public class Conexion {
    // URL de la base de datos
    private static final String url = "jdbc:mysql://localhost/proyecto_bv"; // especifica que la base de datos está alojada en el servidor local (localhost) y que el nombre de la base de datos es proyecto_bv.
    // Nombre del usuario de la base de datos
    private static final String user = "root";
    // Contraseña del usuario de la base de datos
    private static final String password = "";
    
    
    /**
     * Método para obtener una conexión a la base de datos.
     * 
     * @return Un objeto {@link Connection} que representa la conexión a la base de datos.
     */
    public Connection Conexion(){ // Connection se pone porque devuelve un objeto conexion
        Connection conex = null;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            // Obtener la conexión a la base de datos
            conex = DriverManager.getConnection(url, user, password); // Establece la conexión a la base de datos utilizando la URL y las credenciales proporcionadas.
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conex;
    }
    
    /**
     * Método para cerrar los recursos de la base de datos.
     * 
     * @param conex La conexión a la base de datos.
     * @param stat La declaración preparada.
     * @param rs El conjunto de resultados.
     */
    public void close(Connection conex, PreparedStatement stat, ResultSet rs) {
        try {
            // Verificar si el ResultSet no es nulo y cerrarlo si es necesario
            if (rs != null) {
                rs.close();
            }
            // Verificar si el PreparedStatement no es nulo y cerrarlo si es necesario
            if (stat != null) {
                stat.close();
            }
            // Verificar si la Connection no es nula y cerrarla si es necesario
            if (conex != null) {
                conex.close();
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que ocurra durante el cierre de los recursos
            e.printStackTrace(); // Imprimir el error para depuración
        }
    }
}
