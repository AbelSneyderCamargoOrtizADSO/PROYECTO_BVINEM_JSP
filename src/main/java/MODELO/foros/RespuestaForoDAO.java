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

    public void subirRespuesta(String contenido, String idf, int UserDoc) throws SQLException {

        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_respuesta_foro(contenido, fecha_public, id_foro_fk, doc_usua_fk) values(?,NOW(),?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, contenido);
            stat.setInt(2, Integer.parseInt(idf));
            stat.setInt(3, UserDoc);

            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public List<RespuestaClass> mostrarRespuestasPorForo(int foroId) throws SQLException {
        List<RespuestaClass> respuestas = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();

            String query = "SELECT tb_respuesta_foro.*, tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_rol.nom_rol,  tb_grado.nom_grado "
                    + "FROM tb_respuesta_foro "
                    + "JOIN tb_usuarios ON tb_respuesta_foro.doc_usua_fk = tb_usuarios.doc_usua "
                    + "JOIN tb_rol ON tb_usuarios.id_rol_fk = tb_rol.id_rol "
                    + "LEFT JOIN tb_estudiante ON tb_usuarios.doc_usua = tb_estudiante.doc_usua_fk "
                    + "LEFT JOIN tb_grado ON tb_estudiante.id_grado_fk = tb_grado.id_grado "
                    + "WHERE tb_respuesta_foro.id_foro_fk = ? "
                    + "ORDER BY tb_respuesta_foro.fecha_public DESC";

            stat = conex.prepareStatement(query);
            stat.setInt(1, foroId);

            rs = stat.executeQuery();

            while (rs.next()) {
                int idRespu = rs.getInt("id_respu");
                String contenido = rs.getString("contenido");
                String fechaPublic = rs.getString("fecha_public");
                String nombreUsuario = rs.getString("nom_usua") + " " + rs.getString("ape_usua");
                String rolUsuario = rs.getString("nom_rol");
                String gradoEstu = rs.getString("nom_grado");
                int usuarioId = rs.getInt("doc_usua_fk");

                RespuestaClass respuesta = new RespuestaClass(idRespu, contenido, fechaPublic, nombreUsuario, rolUsuario, gradoEstu, usuarioId);
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

    public void editarRespuesta(int respuestaId, String contenido) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_respuesta_foro SET contenido = ? WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, contenido);
            stat.setInt(2, respuestaId);

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    public void eliminarRespuesta(int respuestaId) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "DELETE FROM tb_respuesta_foro WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, respuestaId);

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

}
