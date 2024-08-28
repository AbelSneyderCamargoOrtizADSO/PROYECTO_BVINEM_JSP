/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import MODELO.usuarios.UsuarioClass;
import java.sql.*;

/**
 * Clase que maneja la validación de usuarios durante el proceso de inicio de
 * sesión. Utiliza la clase {@link Conexion} para manejar las conexiones a la
 * base de datos.
 *
 * @see Conexion
 * @see UsuarioClass
 * @see HashUtil
 */
public class login_class {

    /**
     * Método para validar las credenciales de un usuario.
     *
     * @param usuario El objeto {@link UsuarioClass} que contiene las
     * credenciales del usuario.
     * @return Un String que indica el resultado de la validación:
     * <ul>
     * <li>"valido" si las credenciales son correctas y el usuario está
     * habilitado.</li>
     * <li>"contraseña_incorrecta" si la contraseña es incorrecta.</li>
     * <li>"inhabilitado" si el usuario está inhabilitado.</li>
     * <li>"usuario_no_encontrado" si el usuario no existe.</li>
     * <li>"superadmin" si el usuario es un superadministrador.</li>
     * </ul>
     * @throws Exception Si ocurre un error al interactuar con la base de datos.
     */
    public String validarUsuario(UsuarioClass usuario) throws Exception {

        // Creación de una instancia de la clase Conexion para manejar la conexión a la base de datos
        Conexion conexion = new Conexion();

        // Inicialización de los recursos a null para asegurar que se cierren en el bloque finally
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            // Establecer la conexión a la base de datos
            conex = conexion.Conexion();

            // Verificar si el usuario existe en la base de datos
            String queryUsuario = "SELECT id_estado_fk, password, id_rol_fk FROM tb_usuarios WHERE doc_usua = ?";
            stat = conex.prepareStatement(queryUsuario);
            stat.setInt(1, usuario.getDocUsu());
            rs = stat.executeQuery();

            // Si el usuario es encontrado, proceder con la validación
            if (rs.next()) {
                int estado = rs.getInt("id_estado_fk"); // Obtener el estado del usuario
                String storedPassword = rs.getString("password"); // Obtener la contraseña almacenada
                int rol = rs.getInt("id_rol_fk"); // Obtener el rol del usuario
                String hashedPassword = HashUtil.hashPassword(usuario.getPass()); // Hashear la contraseña proporcionada para la validación

                // Verificar si la contraseña es incorrecta
                if (!storedPassword.equals(hashedPassword) && (rol == usuario.getRol() || rol == 4 && usuario.getRol() == 3)) {
                    return "contraseña_incorrecta";
                }

                // Verificar si el usuario está inhabilitado
                if (estado != 1) {
                    return "inhabilitado";
                }

                // Verificar si el rol coincide
                if (rol == usuario.getRol()) {
                    return "valido";
                } else if (rol == 4 && usuario.getRol() == 3) {
                    return "superadmin";
                } else {
                    return "usuario_no_encontrado";
                }

            } else {
                // Si el usuario no es encontrado en la base de datos
                return "usuario_no_encontrado";
            }
        } finally {
            // Cerrar los recursos de la base de datos para evitar fugas de memoria
            conexion.close(conex, stat, rs);
        }
    }
}
