/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

import MODELO.Conexion;
import java.io.InputStream;
import static java.lang.System.out;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class ForoDAO {

    public void subirForo(String tit, String descrip, int UserDoc, String asig, String idioma, String tipo) throws SQLException {

        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_foro(tit_foro, descrip_foro, fecha_creacion, id_asig_fk, doc_usua_fk, id_idioma_fk, id_tpfr_fk) values(?,?,NOW(),?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tit);
            stat.setString(2, descrip);
            stat.setInt(3, Integer.parseInt(asig));
            stat.setInt(4, UserDoc);
            stat.setInt(5, Integer.parseInt(idioma));
            stat.setInt(6, Integer.parseInt(tipo));

            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public List<ForoClass> listarForos() {
        List<ForoClass> foros = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "ORDER BY tb_foro.fecha_creacion DESC";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_foro");
                String titulo = rs.getString("tit_foro");
                String descripcion = rs.getString("descrip_foro");
                String fecha = rs.getString("fecha_creacion");
                String idioma = rs.getString("idioma");
                String asignatura = rs.getString("asignatura");
                String tipo = rs.getString("tipo");

                foros.add(new ForoClass(id, titulo, descripcion, fecha, idioma, asignatura, tipo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return foros;
    }

    public List<ForoClass> filtrarForos(String asignatura, String idioma, String tipo) {
        List<ForoClass> foros = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder(
                "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "WHERE "
        );

        if (asignatura != null && !asignatura.isEmpty()) {
            sql.append("tb_foro.id_asig_fk = ? ");
        }

        if (idioma != null && !idioma.isEmpty()) {
            sql.append("tb_foro.id_idioma_fk = ? ");
        }

        if (tipo != null && !tipo.isEmpty()) {
            sql.append("tb_foro.id_tpfr_fk = ? ");
        }

        sql.append("ORDER BY tb_foro.fecha_creacion DESC");

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql.toString());

            // Inicializa el índice de parámetro para el PreparedStatement.
            int index = 1;

            // Si el filtro de asignatura no es nulo ni vacío, se asigna al parámetro correspondiente en la consulta.
            if (asignatura != null && !asignatura.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(asignatura));
            }

            // Si el filtro de idioma no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (idioma != null && !idioma.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(idioma));
            }

            // Si el filtro de tipo no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (tipo != null && !tipo.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(tipo));
            }

            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_foro");
                String titulo = rs.getString("tit_foro");
                String descripcion = rs.getString("descrip_foro");
                String fecha = rs.getString("fecha_creacion");
                String idiomaResult = rs.getString("idioma");
                String asignaturaResult = rs.getString("asignatura");
                String tipoResult = rs.getString("tipo");

                foros.add(new ForoClass(id, titulo, descripcion, fecha, idiomaResult, asignaturaResult, tipoResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return foros;
    }

    public ForoClass mostrarForoPorId(int id) {
        ForoClass foro = null;
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo, "
                + "tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_rol.nom_rol "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_usuarios ON tb_foro.doc_usua_fk = tb_usuarios.doc_usua "
                + "JOIN tb_rol ON tb_usuarios.id_rol_fk = tb_rol.id_rol "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "WHERE tb_foro.id_foro = ?";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Asignar el valor del parámetro
            stat.setInt(1, id);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            if (rs.next()) {
                int foroId = rs.getInt("id_foro");
                String titulo = rs.getString("tit_foro");
                String descripcion = rs.getString("descrip_foro");
                String fecha = rs.getString("fecha_creacion");
                String idioma = rs.getString("idioma");
                int idiomaId = rs.getInt("id_idioma_fk");
                String asignatura = rs.getString("asignatura");
                int asignaturaId = rs.getInt("id_asig_fk");
                String tipo = rs.getString("tipo");
                int tipoId = rs.getInt("id_tpfr_fk");
                String nombreUsuario = rs.getString("nom_usua") + " " + rs.getString("ape_usua");
                String rolUsuario = rs.getString("nom_rol");
                int usuarioDoc = rs.getInt("doc_usua_fk");

                foro = new ForoClass(foroId, titulo, descripcion, fecha, idioma, idiomaId, asignatura, asignaturaId, tipo, tipoId, nombreUsuario, rolUsuario, usuarioDoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return foro;
    }

    public void editarForo(int id, String descripcion) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_foro SET descrip_foro = ? WHERE id_foro = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, descripcion);
            stat.setInt(2, id);

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public void eliminarForo(int id) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statRespuestas = null;
        PreparedStatement statForo = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false); // Desactivar el autocommit para manejar la transacción manualmente

            // Eliminar respuestas asociadas al foro
            String deleteRespuestas = "DELETE FROM tb_respuesta_foro WHERE id_foro_fk = ?";
            statRespuestas = conex.prepareStatement(deleteRespuestas);
            statRespuestas.setInt(1, id);
            statRespuestas.executeUpdate();

            // Eliminar el foro
            String deleteForo = "DELETE FROM tb_foro WHERE id_foro = ?";
            statForo = conex.prepareStatement(deleteForo);
            statForo.setInt(1, id);
            statForo.executeUpdate();

            // Commit de la transacción
            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback(); // Deshacer la transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            // Cerrar los recursos utilizando el método close de la clase Conexion
            if (statRespuestas != null || statForo != null) {
                conexion.close(conex, statRespuestas, null);
                conexion.close(conex, statForo, null);
            }
            if (conex != null) { // https://puntocomnoesunlenguaje.blogspot.com/2017/11/java-jdbc-transacciones.html
                try {
                    conex.setAutoCommit(true); // Restaurar el autocommit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
