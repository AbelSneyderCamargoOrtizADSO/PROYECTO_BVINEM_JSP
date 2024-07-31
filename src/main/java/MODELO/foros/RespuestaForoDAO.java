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
 *
 * @author Abelito
 */
public class RespuestaForoDAO {
    private Conexion conexion;
    
    public RespuestaForoDAO() {
        this.conexion = new Conexion();
    }

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

    public List<RespuestaClass> mostrarRespuestasPorForo(ForoClass foro) throws SQLException {
        List<RespuestaClass> respuestas = new ArrayList<>();
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

                RespuestaClass respuesta = new RespuestaClass(idRespu, contenido, fechaPublic, nombreUsuario, rolUsuario, usuarioId);
                respuestas.add(respuesta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, rs);
        }

        return respuestas;
    }

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
