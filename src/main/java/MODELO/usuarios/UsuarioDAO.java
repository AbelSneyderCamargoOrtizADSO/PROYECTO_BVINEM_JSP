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
     * @param usuario El objeto {@link UsuarioClass} que contiene los datos del usuario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void agregarUsuario(UsuarioClass usuario) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();

            // Encriptar la contraseña antes de guardarla
            String hashedPassword = HashUtil.hashPassword(usuario.getPass());
            usuario.setPass(hashedPassword);

            // Insertar en tb_usuarios
            String queryUsuario = "INSERT INTO tb_usuarios (doc_usua, nom_usua, ape_usua, correo_usua, password, id_rol_fk, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, usuario.getDocUsu());
            statUsuario.setString(2, usuario.getNombre());
            statUsuario.setString(3, usuario.getApellido());
            statUsuario.setString(4, usuario.getCorreo());
            statUsuario.setString(5, usuario.getPass());
            statUsuario.setInt(6, usuario.getRol());

            statUsuario.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
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
            conex = conexion.Conexion();
            String query = "UPDATE tb_usuarios SET id_estado_fk = ? WHERE doc_usua = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setInt(1, estado);
            statUsuario.setInt(2, usuario.getDocUsu());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
     * @param query La consulta SQL a ejecutar.
     * @param parametro1 El primer parámetro de la consulta SQL.
     * @param parametro2 El segundo parámetro de la consulta SQL (puede ser null).
     * @return Una lista de objetos {@link UsuarioClass} que cumplen con los criterios de la consulta.
     */
    private List<UsuarioClass> mostrarUsuarios(String query, int parametro1, String parametro2) {
        List<UsuarioClass> usuarios = new ArrayList<>();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(query);

            stat.setInt(1, parametro1);
            if (parametro2 != null) {
                stat.setString(2, parametro2);
            }

            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("doc_usua");
                String nombres = rs.getString("nom_usua");
                String apellidos = rs.getString("ape_usua");
                String correo = rs.getString("correo_usua");
                String fecha_reg = rs.getString("fecha_registro");
                int estadoId = rs.getInt("id_estado_fk");
                int rol = rs.getInt("id_rol_fk");


                usuarios.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, estadoId, rol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return usuarios;
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
