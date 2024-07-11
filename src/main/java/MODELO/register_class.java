/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import static java.lang.System.out;
import java.sql.*;
import com.mysql.jdbc.Driver;

/**
 *
 * @author Abelito
 */

public class register_class {

    public void registrarUsuario(int dni, String nom, String ape, String correo, String contra, String grado) throws ClassNotFoundException, SQLException {
        
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statUsuarios = null;
        PreparedStatement statEstudiante = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // CODIGO SQL PARA INSERTAR LOS DATOS INGRESADOS
            // LA FUNCION NOW() LO QUE HACE ES TOMAR FECHA Y HORA ACTUALES
            String sqlUsuarios = "insert into tb_usuarios(doc_usua,nom_usua,ape_usua,correo_usua,password,id_rol_fk,fecha_registro) values(?,?,?,?,?,'1',NOW())";
            statUsuarios = conex.prepareStatement(sqlUsuarios);
            statUsuarios.setInt(1, dni);
            statUsuarios.setString(2, nom);
            statUsuarios.setString(3, ape);
            statUsuarios.setString(4, correo);
            statUsuarios.setString(5, contra);
            statUsuarios.executeUpdate();

            String sqlEstudiante = "insert into tb_estudiante(id_grado_fk,doc_usua_fk) values(?,?)";
            statEstudiante = conex.prepareStatement(sqlEstudiante);
            statEstudiante.setString(1, grado);
            statEstudiante.setInt(2, dni);
            statEstudiante.executeUpdate();

            conex.commit(); // Confirmar la transacción si todo fue exitoso

        } catch (Exception error) {
            if (conex != null) {
                try {
                    conex.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }
            error.printStackTrace(); // Imprimir el error en la consola
        } finally {
            conexion.close(conex, statUsuarios, null);
            conexion.close(null, statEstudiante, null);
        } 
    }
}
