/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import java.io.InputStream;
import java.sql.*;

/**
 * Clase que proporciona métodos de validación para diferentes campos y datos de usuario.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 */
public class Validador {

    private static Conexion conexion;

    static {
        conexion = new Conexion();
    }

    /**
     * Valida las credenciales de inicio de sesión del usuario.
     *
     * @param documento El documento del usuario.
     * @param pass La contraseña del usuario.
     * @return Un mensaje de error si hay algún problema, de lo contrario null.
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
    
    /**
     * Valida el documento del usuario.
     *
     * @param documento El documento a validar.
     * @return Un mensaje de error si hay algún problema, de lo contrario null.
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
    
    /**
     * Valida el nombre del usuario.
     *
     * @param nombre El nombre a validar.
     * @return Un mensaje de error si hay algún problema, de lo contrario null.
     */
    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        if (!nombre.matches("^[A-Za-zÁ-ÿ\\s]+$")) {
            return "Solo se aceptan letras en el nombre";
        }
        return null; // No hay errores
    }

    /**
    * Valida el apellido ingresado.
    * 
    * Este método verifica que el apellido no sea nulo o vacío, y que solo contenga
    * letras (incluyendo acentos) y espacios. Si la validación falla, devuelve un
    * mensaje de error. Si la validación es exitosa, devuelve null.
    * 
    * @param apellido El apellido a validar.
    * @return Un mensaje de error si el apellido es inválido, o null si es válido.
    */
    public static String validarApellido(String apellido) {

        // Verifica si el apellido es nulo o está vacío
        if (apellido == null || apellido.trim().isEmpty()) {
            return "El apellido no puede estar vacío"; // Retorna un mensaje de error si el apellido está vacío
        }

        // Verifica si el apellido contiene solo letras y espacios
        if (!apellido.matches("^[A-Za-zÁ-ÿ\\s]+$")) {
            return "Solo se aceptan letras en el apellido"; // Retorna un mensaje de error si el apellido contiene caracteres no permitidos
        }

        return null; // No hay errores, retorna null
    }

    /**
    * Valida la dirección de correo electrónico ingresada.
    * 
    * Este método verifica que el correo no sea nulo o vacío, y que cumpla con el
    * formato estándar de una dirección de correo electrónico. Si la validación falla,
    * devuelve un mensaje de error. Si la validación es exitosa, devuelve null.
    * 
    * @param correo La dirección de correo electrónico a validar.
    * @return Un mensaje de error si el correo es inválido, o null si es válido.
    */
    public static String validarCorreo(String correo) {

        // Verifica si el correo es nulo o está vacío
        if (correo == null || correo.trim().isEmpty()) {
            return "El correo no puede estar vacío"; // Retorna un mensaje de error si el correo está vacío
        }

        // Expresión regular para validar el formato de correo electrónico
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}$";

        // Verifica si el correo cumple con el formato de correo electrónico
        if (!correo.matches(emailRegex)) {
            return "El correo no es válido"; // Retorna un mensaje de error si el formato del correo es incorrecto
        }

        return null; // No hay errores, retorna null
    }

    
    /**
     * Valida el correo electrónico del usuario.
     *
     * @param correo El correo a validar.
     * @return Un mensaje de error si hay algún problema, de lo contrario null.
     */
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
    
    /**
    * Valida si el correo electrónico ya está en uso por otro usuario.
    *
    * Este método verifica en la base de datos si el correo proporcionado ya está
    * asociado a otro usuario que no sea el usuario actual. Si el correo ya está en uso,
    * devuelve un mensaje de error; de lo contrario, devuelve null.
    *
    * @param correo El correo a validar.
    * @param docActual El documento actual del usuario.
    * @return Un mensaje de error si el correo ya está en uso, de lo contrario null.
    */
    public static String validarCorreoEnUso(String correo, int docActual) {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement statement = null; // Sentencia SQL preparada
        ResultSet resultSet = null; // Resultados de la consulta
        boolean existe = false; // Bandera para verificar si el correo ya existe

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();
            // Consulta SQL para verificar si el correo ya está en uso por otro usuario
            String query = "SELECT 1 FROM tb_usuarios WHERE correo_usua = ? AND doc_usua != ?";
            statement = conex.prepareStatement(query);
            statement.setString(1, correo); // Asigna el correo al primer parámetro
            statement.setInt(2, docActual); // Asigna el documento actual al segundo parámetro
            resultSet = statement.executeQuery(); // Ejecuta la consulta

            if (resultSet.next()) {
                existe = true; // Si hay un resultado, el correo ya está en uso
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error en la consulta
            return "Error al verificar el correo: " + e.getMessage(); // Retorna un mensaje de error
        } finally {
            // Cierra la conexión, la sentencia y el conjunto de resultados
            conexion.close(conex, statement, resultSet);
        }

        if (existe) {
            return "El correo ya está en uso."; // Retorna un mensaje si el correo ya está en uso
        }
        return null; // No hay errores, el correo no está en uso
    }

    
    /**
    * Valida si el documento ya está en uso por otro usuario.
    *
    * Este método verifica en la base de datos si el documento proporcionado ya está
    * asociado a otro usuario. Si el documento ya está en uso, devuelve un mensaje de
    * error; de lo contrario, devuelve null.
    *
    * @param documento El documento a validar.
    * @return Un mensaje de error si el documento ya está en uso, de lo contrario null.
    */
    public static String validarDocumentoEnUso(String documento) {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement statement = null; // Sentencia SQL preparada
        ResultSet resultSet = null; // Resultados de la consulta
        boolean existe = false; // Bandera para verificar si el documento ya existe

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para verificar si el documento ya está en uso
            String query = "SELECT 1 FROM tb_usuarios WHERE doc_usua = ?";
            statement = conex.prepareStatement(query);
            statement.setString(1, documento); // Asigna el documento al primer parámetro
            resultSet = statement.executeQuery(); // Ejecuta la consulta

            // Verifica si hay un resultado, lo que indicaría que el documento ya está en uso
            if (resultSet.next()) {
                existe = true; 
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error en la consulta
            return "Error al verificar el documento: " + e.getMessage(); // Retorna un mensaje de error con los detalles de la excepción
        } finally {
            // Cierra la conexión, la sentencia y el conjunto de resultados
            conexion.close(conex, statement, resultSet);
        }

        // Retorna un mensaje si el documento ya está en uso, de lo contrario retorna null
        if (existe) {
            return "El documento ya está en uso.";
        }
        return null; // No hay errores, el documento no está en uso
    }


    // FOROS

    /**
     * Valida el título de un foro.
     *
     * Este método verifica que el título no sea nulo, vacío, y que no exceda los 50 caracteres.
     * 
     * @param tit El título a validar.
     * @return Un mensaje de error si el título es inválido, de lo contrario null.
     */
    public static String validarTitulo(String tit) {
        // Verifica si el título es nulo o está vacío
        if (tit == null || tit.trim().isEmpty()) {
            return "El título no puede estar vacío"; // Retorna un mensaje de error si el título está vacío
        }
        // Verifica si el título excede los 50 caracteres
        if (tit.length() > 50) {
            return "El título debe tener máximo 50 caracteres"; // Retorna un mensaje de error si el título es demasiado largo
        }
        return null; // No hay errores
    }

    /**
     * Valida la descripción de un foro.
     *
     * Este método verifica que la descripción no sea nula, vacía, o simplemente un espacio vacío en HTML.
     * 
     * @param descrip La descripción a validar.
     * @return Un mensaje de error si la descripción es inválida, de lo contrario null.
     */
    public static String validarDescripcion(String descrip) {
        // Verifica si la descripción es nula, está vacía o es un espacio vacío en HTML
        if (descrip == null || descrip.trim().isEmpty() || descrip.equals("<p><br></p>")) {
            return "La descripción no puede estar vacía"; // Retorna un mensaje de error si la descripción está vacía
        }
        return null; // No hay errores
    }

    /**
     * Valida la asignatura seleccionada para un foro.
     *
     * Este método verifica que la asignatura no sea nula o vacía.
     * 
     * @param asig La asignatura a validar.
     * @return Un mensaje de error si no se ha seleccionado una asignatura, de lo contrario null.
     */
    public static String validarAsignatura(String asig) {
        // Verifica si la asignatura es nula o está vacía
        if (asig == null || asig.trim().isEmpty()) {
            return "Por favor, seleccione la asignatura"; // Retorna un mensaje de error si no se ha seleccionado una asignatura
        }
        return null; // No hay errores
    }

    /**
     * Valida el idioma seleccionado para un foro.
     *
     * Este método verifica que el idioma no sea nulo o vacío.
     * 
     * @param idioma El idioma a validar.
     * @return Un mensaje de error si no se ha seleccionado un idioma, de lo contrario null.
     */
    public static String validarIdioma(String idioma) {
        // Verifica si el idioma es nulo o está vacío
        if (idioma == null || idioma.trim().isEmpty()) {
            return "Por favor, seleccione el idioma"; // Retorna un mensaje de error si no se ha seleccionado un idioma
        }
        return null; // No hay errores
    }

    /**
     * Valida el tipo de foro seleccionado.
     *
     * Este método verifica que el tipo de foro no sea nulo o vacío.
     * 
     * @param tipo El tipo de foro a validar.
     * @return Un mensaje de error si no se ha seleccionado un tipo de foro, de lo contrario null.
     */
    public static String validarTipoForo(String tipo) {
        // Verifica si el tipo de foro es nulo o está vacío
        if (tipo == null || tipo.trim().isEmpty()) {
            return "Por favor, seleccione el tipo"; // Retorna un mensaje de error si no se ha seleccionado un tipo de foro
        }
        return null; // No hay errores
    }


    // DOCUMENTOS - LIBROS

    /**
     * Valida el nombre del autor de un documento o libro.
     *
     * Este método verifica que el nombre del autor no sea nulo o vacío.
     * 
     * @param autor El nombre del autor a validar.
     * @return Un mensaje de error si el nombre del autor es inválido, de lo contrario null.
     */
    public static String validarAutor(String autor) {
        // Verifica si el nombre del autor es nulo o está vacío
        if (autor == null || autor.trim().isEmpty()) {
            return "Por favor, ingrese el autor"; // Retorna un mensaje de error si no se ha ingresado un autor
        }
        return null; // No hay errores
    }

    /**
     * Valida el año de publicación de un documento o libro.
     *
     * Este método verifica que el año de publicación no sea nulo o vacío.
     * 
     * @param year El año de publicación a validar.
     * @return Un mensaje de error si el año es inválido, de lo contrario null.
     */
    public static String validarYear(String year) {
        // Verifica si el año de publicación es nulo o está vacío
        if (year == null || year.trim().isEmpty()) {
            return "Por favor, ingrese el año de publicación"; // Retorna un mensaje de error si no se ha ingresado un año
        }
        return null; // No hay errores
    }

    
    /**
     * Valida si una categoría ya existe en la base de datos.
     *
     * @param nombre El nombre de la categoría.
     * @param tipoCategoria El tipo de categoría (asignatura, idioma, tipo documento, tipo foro).
     * @param id El ID de la categoría actual.
     * @return Un mensaje de error si la categoría ya existe, de lo contrario null.
     */
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
