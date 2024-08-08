/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import MODELO.usuarios.UsuarioClass;
import java.sql.*;

/**
 * Clase que maneja la validación de usuarios durante el proceso de inicio de sesión.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see UsuarioClass
 * @see HashUtil
 */
public class login_class {
    
    /**
     * Método para validar las credenciales de un usuario.
     * 
     * @param usuario El objeto {@link UsuarioClass} que contiene las credenciales del usuario.
     * @return Un String que indica el resultado de la validación:
     * <ul>
     *   <li>"valido" si las credenciales son correctas y el usuario está habilitado.</li>
     *   <li>"contraseña_incorrecta" si la contraseña es incorrecta.</li>
     *   <li>"inhabilitado" si el usuario está inhabilitado.</li>
     *   <li>"usuario_no_encontrado" si el usuario no existe.</li>
     *   <li>"superadmin" si el usuario es un superadministrador.</li>
     * </ul>
     * @throws Exception Si ocurre un error al interactuar con la base de datos.
     */
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
