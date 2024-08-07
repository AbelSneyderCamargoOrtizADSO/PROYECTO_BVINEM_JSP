/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import java.io.InputStream;
import java.sql.*;

/**
 *
 * @author Abelito
 */
public class Validador {

    private static Conexion conexion;

    static {
        conexion = new Conexion();
    }

    /**
     * Valida el documento del usuario.
     *
     * @param documento El documento a validar.
     * @return Mensaje de error si hay algún problema, de lo contrario null.
     */
    public static String validarLogin(String documento, String pass) { // los metodos estaticos son llamados sin necesidad de instancias un objeto de la clase
        if (documento == null || documento.trim().isEmpty()) {
            return "El documento no puede estar vacío";
        }
        if (pass == null || pass.trim().isEmpty()) {
            return "La contraseña no puede estar vacía";
        }
        return null; // No hay errores
    }

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

    public static String validarGrado(String grado) {
        if (grado == null || grado.trim().isEmpty()) {
            return "El grado del estudiante no puede estar vacìo";
        }
        if (!grado.matches("^[0-9]+$")) {
            return "Solo se aceptan números en el grado";
        }
        return null; // No hay errores
    }

    public static String validarCorreoEnUso(String correo, int docActual) {
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

    public static String validarDocumentoEnUso(String documento) {
        Connection conex = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean existe = false;

        try {
            conex = conexion.Conexion();
            String query = "SELECT 1 FROM tb_usuarios WHERE doc_usua = ?";
            statement = conex.prepareStatement(query);
            statement.setString(1, documento);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al verificar el documento: " + e.getMessage();
        } finally {
            conexion.close(conex, statement, resultSet);
        }

        if (existe) {
            return "El documento ya está en uso.";
        }
        return null; // No hay errores
    }

    // FOROS
    public static String validarTitulo(String tit) {
        if (tit == null || tit.trim().isEmpty()) {
            return "El título no puede estar vacío";
        }
        if (tit.length() > 50) {
            return "El título debe tener maximo 50 caracteres";
        }
        return null;
    }

    public static String validarDescripcion(String descrip) {
        if (descrip == null || descrip.trim().isEmpty() || descrip.equals("<p><br></p>")) {
            return "La descripción no puede estar vacía";
        }
        return null;
    }

    public static String validarAsignatura(String asig) {
        if (asig == null || asig.trim().isEmpty()) {
            return "Por favor, seleccione la asignatura";
        }
        return null;
    }

    public static String validarIdioma(String idioma) {
        if (idioma == null || idioma.trim().isEmpty()) {
            return "Por favor, seleccione el idioma";
        }
        return null;
    }

    public static String validarTipoForo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return "Por favor, seleccione el tipo";
        }
        return null;
    }

    // DOCUMENTOS - LIBROS
    public static String validarAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            return "Por favor, ingrese el autor";
        }
        return null;
    }

    public static String validarYear(String year) {
        if (year == null || year.trim().isEmpty()) {
            return "Por favor, ingrese el año de publicación";
        }
        return null;
    }

    public static String validarCategoria(String nombre, String tipoCategoria, int id) {
        Connection conex = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean existe = false;

        try {
            conex = conexion.Conexion();
            String query = "";
            switch (tipoCategoria) {
                case "asignatura":
                    query = "SELECT id_asig FROM tb_asignaturas WHERE nom_asig = ?";
                    break;
                case "idioma":
                    query = "SELECT id_idioma FROM tb_idiomas WHERE nom_idioma = ?";
                    break;
                case "tipo documento":
                    query = "SELECT id_tipo FROM tb_tipo_doc WHERE nom_tipo = ?";
                    break;
                case "tipo foro":
                    query = "SELECT id_tp_foro FROM tb_tipo_foro WHERE nom_tp_foro = ?";
                    break;
            }

            statement = conex.prepareStatement(query);
            statement.setString(1, nombre);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int existeconeseId = resultSet.getInt(1); // Obtiene el ID del registro encontrado
                if (existeconeseId != id) { // Compara con el ID del registro actual para ver si es un registro diferente
                    existe = true; // Indica que ya existe otro registro con el mismo nombre
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al verificar la categoría: " + e.getMessage();
        } finally {
            conexion.close(conex, statement, resultSet);
        }

        if (existe) {
            return tipoCategoria + " ya existente";
        }
        return null; // No hay errores
    }

}
