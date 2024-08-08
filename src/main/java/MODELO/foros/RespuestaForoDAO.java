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
     * @param respuesta El objeto {@link RespuestaClass} que contiene los datos de la respuesta.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void subirRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_respuesta_foro(contenido, fecha_public, id_foro_fk, doc_usua_fk) values(?,NOW(),?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido());
            stat.setInt(2, respuesta.getIdForo());
            stat.setInt(3, respuesta.getUsuarioId());

            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
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
     * @param respuesta El objeto {@link RespuestaClass} que contiene los datos actualizados de la respuesta.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void editarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_respuesta_foro SET contenido = ? WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido());
            stat.setInt(2, respuesta.getId());

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    /**
     * Método para eliminar una respuesta del foro.
     * 
     * @param respuesta El objeto {@link RespuestaClass} que representa la respuesta a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "DELETE FROM tb_respuesta_foro WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, respuesta.getId());

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

}
