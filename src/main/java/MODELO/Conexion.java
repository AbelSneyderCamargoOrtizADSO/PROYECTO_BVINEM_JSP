/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.*;
/**
 *
 * @author Abelito
 */
public class Conexion {
    // URL de la base de datos
    private static final String url = "jdbc:mysql://localhost/proyecto_bv";
    // Nombre del usuario de la base de datos
    private static final String user = "root";
    // Contraseña del usuario de la base de datos
    private static final String password = "";
    
    
    // Método para obtener una conexión a la base de datos
    public Connection Conexion(){ // Connection se pone porque devuelve un objeto conexion
        Connection conex = null;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Obtener la conexión a la base de datos
            conex = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conex;
    }
    
    public void close(Connection conex, PreparedStatement stat, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conex != null) {
                conex.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
