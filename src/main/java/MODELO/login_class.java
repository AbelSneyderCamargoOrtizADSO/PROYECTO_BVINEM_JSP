/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import MODELO.usuarios.UsuarioClass;
import java.sql.*;

/**
 *
 * @author Abelito
 */
public class login_class {

    public String validarUsuario(UsuarioClass usuario) throws Exception {

        // creamos la instancia de la clase conexion 
        Conexion conexion = new Conexion();

        // Se inicilizan en null para asegurar que se puedan cerrar en el bloque finally
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();

            // Verificar si el usuario existe
            String queryUsuario = "SELECT id_estado_fk, password, id_rol_fk FROM tb_usuarios WHERE doc_usua = ?";
            stat = conex.prepareStatement(queryUsuario);
            stat.setInt(1, usuario.getDocUsu());
            rs = stat.executeQuery();

            if (rs.next()) {
                // Usuario encontrado, verificar la contraseña
                int estado = rs.getInt("id_estado_fk");
                String storedPassword = rs.getString("password");
                int rol = rs.getInt("id_rol_fk");
                String hashedPassword = HashUtil.hashPassword(usuario.getPass());
                
                if (!storedPassword.equals(hashedPassword) && (rol == usuario.getRol() || rol == 4 && usuario.getRol() == 3)) {
                    return "contraseña_incorrecta";
                }

                if (estado != 1) {
                    return "inhabilitado";
                }

                if (rol == usuario.getRol()) {
                    return "valido";
                } else if (rol == 4 && usuario.getRol() == 3) {
                    return "superadmin";
                } else {
                    return "usuario_no_encontrado";
                }

            } else {
                // Usuario no encontrado
                return "usuario_no_encontrado";
            }
        } finally {
            conexion.close(conex, stat, rs);
        }
    }
}
