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
public class login_class {
    
    public boolean validarUsuario(String dni, String pass, String rol) throws Exception{
        
        // creamos la instancia de la clase conexion 
        Conexion conexion = new Conexion();
        
        // Se inicilizan en null para asegurar que se puedan cerrar en el bloque finally
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        try {
            conex = conexion.Conexion();
            
            // Hash
            String hashedPassword = HashUtil.hashPassword(pass);
                
            String query = "SELECT * FROM `tb_usuarios` where doc_usua = ? and password = ? AND id_rol_fk = ?";

            stat = conex.prepareStatement(query);
            stat.setString(1, dni);
            stat.setString(2, hashedPassword);
            stat.setString(3, rol);
            rs = stat.executeQuery();
            
            return rs.next();
                
        } finally {
            conexion.close(conex, stat, rs);
        }
    }
    
}
