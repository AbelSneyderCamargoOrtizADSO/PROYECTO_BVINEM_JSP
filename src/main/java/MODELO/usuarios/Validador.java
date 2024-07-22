/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import java.sql.*;

/**
 *
 * @author Abelito
 */
public class Validador {

    /**
     * Valida el documento del usuario.
     *
     * @param documento El documento a validar.
     * @return Mensaje de error si hay algún problema, de lo contrario null.
     */
    public static String validarDocumento(String documento) { // los metodos estaticos son llamados sin necesidad de instancias un objeto de la clase
        if (documento == null || documento.trim().isEmpty()) {
            return "El documento no puede estar vacío";
        }
        if (!documento.matches("^[0-9]+$")) {
            return "Solo se aceptan números en el documento";
        }
        return null; // No hay errores
    }

    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        if (!nombre.matches("^[A-Za-zÁ-ÿ\\s]+$")) {
            return "Solo se aceptan letras en el nombre";
        }
        return null; // No hay errores
    }

    public static String validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return "El apellido no puede estar vacío";
        }
        if (!apellido.matches("^[A-Za-zÁ-ÿ\\s]+$")) {
            return "Solo se aceptan letras en el apellido";
        }
        return null; // No hay errores
    }

    public static String validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return "El correo no puede estar vacío";
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}$";
        if (!correo.matches(emailRegex)) {
            return "El correo no es válido";
        }
        return null; // No hay errores
    }

    public static String validarContrasena(String pass) {
        if (pass == null || pass.trim().isEmpty()) {
            return "La contraseña no puede estar vacía";
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (!pass.matches(regex)) {
            return "La contraseña debe tener al menos 8 caracteres y contener letras mayúsculas, minúsculas y números";
        }
        return null; // No hay errores
    }

    public static String validarCorreoEnUso(String correo, int docActual) {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean existe = false;

        try {
            conex = conexion.Conexion();
            String query = "SELECT 1 FROM tb_usuarios WHERE correo_usua = ? AND doc_usua != ?";
            statement = conex.prepareStatement(query);
            statement.setString(1, correo);
            statement.setInt(2, docActual);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al verificar el correo: " + e.getMessage();
        } finally {
            conexion.close(conex, statement, resultSet);
        }

        if (existe) {
            return "El correo ya está en uso.";
        }
        return null; // No hay errores
    }

}
