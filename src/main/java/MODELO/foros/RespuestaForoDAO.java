/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

import MODELO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de la base de datos relacionadas con las respuestas de los foros.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @author Abel Camargo
 * @see Conexion
 * @see RespuestaClass
 * @see ForoClass
 */

public class RespuestaForoDAO {
    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public RespuestaForoDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para subir una nueva respuesta al foro.
    * 
    * Este método inserta una nueva respuesta en la tabla `tb_respuesta_foro` de la base de datos,
    * estableciendo el contenido de la respuesta, la fecha de publicación actual, el ID del foro al 
    * que pertenece la respuesta, y el ID del usuario que publica la respuesta.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que contiene los datos de la respuesta.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void subirRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar una nueva respuesta en el foro
            String query = "INSERT INTO tb_respuesta_foro(contenido, fecha_public, id_foro_fk, doc_usua_fk) VALUES(?,NOW(),?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido()); // Establece el contenido de la respuesta
            stat.setInt(2, respuesta.getIdForo()); // Establece el ID del foro al que pertenece la respuesta
            stat.setInt(3, respuesta.getUsuarioId()); // Establece el ID del usuario que publica la respuesta

            stat.executeUpdate(); // Ejecuta la inserción en la base de datos

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, stat, null);
        }
    }

    
    /**
     * Método para obtener una lista de respuestas de un foro específico.
     * 
     * @param foro El objeto {@link ForoClass} que representa el foro.
     * @return Una lista de objetos {@link RespuestaClass} que contiene las respuestas del foro.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<RespuestaClass> mostrarRespuestasPorForo(ForoClass foro) throws SQLException {
        List<RespuestaClass> respuestas = new ArrayList<>(); // Crea una nueva lista de objetos RespuestaClass utilizando ArrayList para almacenar las respuestas del foro.
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();

            String query = "SELECT tb_respuesta_foro.*, tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_rol.nom_rol "
                    + "FROM tb_respuesta_foro "
                    + "JOIN tb_usuarios ON tb_respuesta_foro.doc_usua_fk = tb_usuarios.doc_usua "
                    + "JOIN tb_rol ON tb_usuarios.id_rol_fk = tb_rol.id_rol "
                    + "WHERE tb_respuesta_foro.id_foro_fk = ? "
                    + "ORDER BY tb_respuesta_foro.fecha_public DESC";

            stat = conex.prepareStatement(query);
            stat.setInt(1, foro.getId());

            rs = stat.executeQuery();

            while (rs.next()) {
                int idRespu = rs.getInt("id_respu");
                String contenido = rs.getString("contenido");
                String fechaPublic = rs.getString("fecha_public");
                String nombreUsuario = rs.getString("nom_usua") + " " + rs.getString("ape_usua");
                String rolUsuario = rs.getString("nom_rol");
                int usuarioId = rs.getInt("doc_usua_fk");
                
                // Crea una nueva instancia del objeto RespuestaClass con los datos obtenidos de la base de datos:
                // idRespu, contenido, fechaPublic, nombreUsuario, rolUsuario y usuarioId.
                RespuestaClass respuesta = new RespuestaClass(idRespu, contenido, fechaPublic, nombreUsuario, rolUsuario, usuarioId);
                respuestas.add(respuesta); // Añade la respuesta creada a la lista de respuestas.
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, rs);
        }

        return respuestas;
    }
    
    /**
    * Método para editar una respuesta existente en el foro.
    * 
    * Este método actualiza el contenido de una respuesta existente en la tabla `tb_respuesta_foro` 
    * de la base de datos, utilizando el ID de la respuesta para identificar cuál se debe actualizar.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que contiene los datos actualizados de la respuesta.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el contenido de una respuesta en el foro
            String query = "UPDATE tb_respuesta_foro SET contenido = ? WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido()); // Establece el nuevo contenido de la respuesta
            stat.setInt(2, respuesta.getId()); // Establece el ID de la respuesta a actualizar

            stat.executeUpdate(); // Ejecuta la actualización en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, stat, null);
        }
    }

   /**
    * Método para eliminar una respuesta del foro.
    * 
    * Este método elimina una respuesta existente de la tabla `tb_respuesta_foro` de la base de datos,
    * utilizando el ID de la respuesta para identificar cuál se debe eliminar.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que representa la respuesta a eliminar.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void eliminarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para eliminar una respuesta del foro
            String query = "DELETE FROM tb_respuesta_foro WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, respuesta.getId()); // Establece el ID de la respuesta a eliminar

            stat.executeUpdate(); // Ejecuta la eliminación en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, stat, null);
        }
    }

}
