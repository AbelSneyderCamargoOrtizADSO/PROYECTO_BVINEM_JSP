/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import MODELO.HashUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de la base de datos relacionadas con los usuarios.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see UsuarioClass
 * @see HashUtil
 * 
 * @author Abel Camargo
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */
public class UsuarioDAO {
   
    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public UsuarioDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para agregar un nuevo usuario a la base de datos.
    * 
    * Este método toma un objeto {@link UsuarioClass} que contiene los datos del usuario,
    * encripta la contraseña, y luego inserta un nuevo registro en la tabla `tb_usuarios`.
    * 
    * @param usuario El objeto {@link UsuarioClass} que contiene los datos del usuario.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void agregarUsuario(UsuarioClass usuario) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement statUsuario = null; // Sentencia SQL preparada para insertar el usuario

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Encriptar la contraseña antes de guardarla
            String hashedPassword = HashUtil.hashPassword(usuario.getPass());
            usuario.setPass(hashedPassword); // Actualiza la contraseña del usuario con la versión encriptada

            // Insertar en la tabla tb_usuarios
            String queryUsuario = "INSERT INTO tb_usuarios (doc_usua, nom_usua, ape_usua, correo_usua, password, id_rol_fk, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, usuario.getDocUsu()); // Establece el documento del usuario
            statUsuario.setString(2, usuario.getNombre()); // Establece el nombre del usuario
            statUsuario.setString(3, usuario.getApellido()); // Establece el apellido del usuario
            statUsuario.setString(4, usuario.getCorreo()); // Establece el correo del usuario
            statUsuario.setString(5, usuario.getPass()); // Establece la contraseña encriptada del usuario
            statUsuario.setInt(6, usuario.getRol()); // Establece el rol del usuario

            statUsuario.executeUpdate(); // Ejecuta la inserción en la base de datos

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error durante la inserción
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, statUsuario, null);
        }
    }

    
    /**
     * Método para editar un usuario existente en la base de datos.
     * 
     * @param docAnterior El documento de identificación anterior del usuario.
     * @param usuario El objeto {@link UsuarioClass} que contiene los datos actualizados del usuario.
     * @param actualizarDocumentos Indica si se deben actualizar los documentos asociados al usuario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     * @see <a href="https://puntocomnoesunlenguaje.blogspot.com/2017/11/java-jdbc-transacciones.html">Transacciones en Java JDBC</a>
     */
    public void editarUsuario(int docAnterior, UsuarioClass usuario, boolean actualizarDocumentos) throws SQLException {
        Connection conex = null;
        PreparedStatement statement = null;
        PreparedStatement disableFKChecks = null;
        PreparedStatement enableFKChecks = null;

        try {
            conex = conexion.Conexion();
            // La transaccion sirve para ejecutar en bloque varias sentencias relacionadas, manteniendo principios, tratando las sentancias como un unico bloque, si una sale mal la otra falla
            // Desactivar el autocommit para manejar la transacción manualmente.
            // Esto permite ejecutar varias sentencias SQL como una sola transacción.
            // Si una sentencia falla, se pueden revertir todas las operaciones de la transacción.
            conex.setAutoCommit(false); // Desactivar el autocommit para manejar la transacción manualmente

            // Deshabilitar la verificación de claves foráneas
            disableFKChecks = conex.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            disableFKChecks.executeUpdate();

            // Actualizar en tb_respuestas
            String queryRespuestas = "UPDATE tb_respuesta_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statement = conex.prepareStatement(queryRespuestas);
            statement.setInt(1, usuario.getDocUsu());
            statement.setInt(2, docAnterior);
            statement.executeUpdate();

            // Actualizar en tb_foro
            String queryForos = "UPDATE tb_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statement = conex.prepareStatement(queryForos);
            statement.setInt(1, usuario.getDocUsu());
            statement.setInt(2, docAnterior);
            statement.executeUpdate();

            // Si es necesario actualizar tb_documentos (solo para docentes)
            if (actualizarDocumentos) {
                String queryDocumentos = "UPDATE tb_documento SET doc_docente_fk = ? WHERE doc_docente_fk = ?";
                statement = conex.prepareStatement(queryDocumentos);
                statement.setInt(1, usuario.getDocUsu());
                statement.setInt(2, docAnterior);
                statement.executeUpdate();
            }

            // Actualizar en tb_usuarios
            String queryUsuario;
            if (usuario.getPass() != null) {
                // Encriptar la nueva contraseña antes de actualizar
                String hashedPassword = HashUtil.hashPassword(usuario.getPass());
                usuario.setPass(hashedPassword);
                queryUsuario = "UPDATE tb_usuarios SET doc_usua = ?, nom_usua = ?, ape_usua = ?, correo_usua = ?, password = ?, id_rol_fk = ? WHERE doc_usua = ?";
            } else {
                queryUsuario = "UPDATE tb_usuarios SET doc_usua = ?, nom_usua = ?, ape_usua = ?, correo_usua = ?, id_rol_fk = ? WHERE doc_usua = ?";
            }
            statement = conex.prepareStatement(queryUsuario);
            statement.setInt(1, usuario.getDocUsu());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getCorreo());
            if (usuario.getPass() != null) {
                statement.setString(5, usuario.getPass());
                statement.setInt(6, usuario.getRol());
                statement.setInt(7, docAnterior);
            } else {
                statement.setInt(5, usuario.getRol());
                statement.setInt(6, docAnterior);
            }
            statement.executeUpdate();

            // Habilitar la verificación de claves foráneas
            enableFKChecks = conex.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
            enableFKChecks.executeUpdate();
            
            // Confirmar la transacción.
            // Esto asegura que todas las operaciones realizadas en la transacción se hagan efectivas en la base de datos.
            // CONFIRMA CAMBIOS
            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                // Revertir la transacción en caso de error.
                // Esto deshace todas las operaciones realizadas en la transacción actual.
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (disableFKChecks != null) {
                disableFKChecks.close();
            }
            if (enableFKChecks != null) {
                enableFKChecks.close();
            }

            conexion.close(conex, statement, null);
        }
    }
    
    /**
     * Método para cambiar el estado de un usuario (habilitado/inhabilitado).
     * 
     * @param usuario El objeto {@link UsuarioClass} que representa el usuario cuyo estado se va a cambiar.
     * @param estado El nuevo estado del usuario (0 para inhabilitado, 1 para habilitado).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void cambiarEstadoUsuario(UsuarioClass usuario, int estado) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion(); // Establecer la conexión a la base de datos
            // Definir la consulta SQL para actualizar el estado del usuario
            String query = "UPDATE tb_usuarios SET id_estado_fk = ? WHERE doc_usua = ?";
            statUsuario = conex.prepareStatement(query); // Preparar la declaración SQL con la consulta definida
            statUsuario.setInt(1, estado); // Asignar el valor del estado al primer parámetro de la consulta
            statUsuario.setInt(2, usuario.getDocUsu()); // Asignar el documento del usuario al segundo parámetro de la consulta
            statUsuario.executeUpdate();  // Ejecutar la actualización en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la traza del error en caso de que ocurra una excepción SQL
            throw e; // Relanzar la excepción para que sea manejada por el método que llama
        }
    }
    
    /**
     * Método para inhabilitar un usuario.
     * 
     * @param usuario El objeto {@link UsuarioClass} que representa el usuario que se va a inhabilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void inhabilitarUsuario(UsuarioClass usuario) throws SQLException {
        cambiarEstadoUsuario(usuario, 0);
    }
    
    /**
     * Método para habilitar un usuario.
     * 
     * @param usuario El objeto {@link UsuarioClass} que representa el usuario que se va a habilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void habilitarUsuario(UsuarioClass usuario) throws SQLException {
        cambiarEstadoUsuario(usuario, 1);
    }
    
    /**
    * Método privado para ejecutar una consulta SQL y retornar una lista de usuarios.
    * 
    * Este método se encarga de ejecutar la consulta SQL proporcionada, establecer los parámetros
    * correspondientes, y construir una lista de objetos {@link UsuarioClass} con los resultados.
    * 
    * @param query La consulta SQL a ejecutar.
    * @param parametro1 El primer parámetro de la consulta SQL, que es un entero.
    * @param parametro2 El segundo parámetro de la consulta SQL, que es una cadena (puede ser null).
    * @return Una lista de objetos {@link UsuarioClass} que cumplen con los criterios de la consulta.
    */
    private List<UsuarioClass> mostrarUsuarios(String query, int parametro1, String parametro2) {
        List<UsuarioClass> usuarios = new ArrayList<>(); // Lista que contendrá los usuarios resultantes
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada
        ResultSet rs = null; // ResultSet para almacenar los resultados de la consulta

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();
            stat = conex.prepareStatement(query); // Prepara la consulta SQL

            // Establece el primer parámetro de la consulta
            stat.setInt(1, parametro1);

            // Establece el segundo parámetro de la consulta si no es nulo
            if (parametro2 != null) {
                stat.setString(2, parametro2);
            }

            rs = stat.executeQuery(); // Ejecuta la consulta y obtiene los resultados

            // Itera sobre los resultados y construye objetos UsuarioClass
            while (rs.next()) {
                int id = rs.getInt("doc_usua"); // Obtiene el documento del usuario
                String nombres = rs.getString("nom_usua"); // Obtiene el nombre del usuario
                String apellidos = rs.getString("ape_usua"); // Obtiene el apellido del usuario
                String correo = rs.getString("correo_usua"); // Obtiene el correo del usuario
                String fecha_reg = rs.getString("fecha_registro"); // Obtiene la fecha de registro
                int estadoId = rs.getInt("id_estado_fk"); // Obtiene el estado del usuario
                int rol = rs.getInt("id_rol_fk"); // Obtiene el rol del usuario

                // Agrega el objeto UsuarioClass a la lista de usuarios
                usuarios.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, estadoId, rol));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
        } finally {
            // Cierra la conexión, la sentencia preparada y el ResultSet
            conexion.close(conex, stat, rs);
        }

        return usuarios; // Retorna la lista de usuarios
    }
    
    /**
     * Método para listar todos los usuarios de un rol específico.
     * 
     * @param rol El rol de los usuarios que se desea listar.
     * @return Una lista de objetos {@link UsuarioClass} que pertenecen al rol especificado.
     */
    public List<UsuarioClass> listarUsuarios(int rol) {
        String query = "SELECT * FROM tb_usuarios WHERE id_rol_fk = ? ORDER BY fecha_registro DESC";
        return mostrarUsuarios(query, rol, null);
    }
    
    /**
     * Método para buscar un usuario por su documento de identificación.
     * 
     * @param rol El rol de los usuarios que se desea buscar.
     * @param docUsuario El documento de identificación del usuario que se desea buscar.
     * @return Una lista de objetos {@link UsuarioClass} que cumplen con los criterios de búsqueda.
     */
     public List<UsuarioClass> buscarUsuarioPorDocumento(int rol, String docUsuario) {
        String query = "SELECT * FROM tb_usuarios WHERE id_rol_fk = ? AND doc_usua LIKE ?";
        return mostrarUsuarios(query,rol, docUsuario + "%");
    }
}
