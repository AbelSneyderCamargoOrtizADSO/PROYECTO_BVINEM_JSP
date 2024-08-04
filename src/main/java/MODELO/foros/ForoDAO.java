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
    
    private Conexion conexion;
    
    public ForoDAO() {
        this.conexion = new Conexion();
    }

    public void subirForo(ForoClass foro) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_foro(tit_foro, descrip_foro, fecha_creacion, id_asig_fk, doc_usua_fk, id_idioma_fk, id_tpfr_fk) values(?,?,NOW(),?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, foro.getTitulo());
            stat.setString(2, foro.getDescripcion());
            stat.setInt(3, foro.getAsignaturaId());
            stat.setInt(4, foro.getUsuarioDoc());
            stat.setInt(5, foro.getIdiomaId());
            stat.setInt(6, foro.getTipoId());

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

    public List<ForoClass> filtrarForos(ForoClass filtro) {
        List<ForoClass> foros = new ArrayList<>();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder(
                "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "WHERE 1=1 "
        );

        if (filtro.getAsignaturaId() > 0) {
            sql.append("AND tb_foro.id_asig_fk = ? ");
        }

        if (filtro.getIdiomaId() > 0) {
            sql.append("AND tb_foro.id_idioma_fk = ? ");
        }

        if (filtro.getTipoId() > 0) {
            sql.append("AND tb_foro.id_tpfr_fk = ? ");
        }

        sql.append("ORDER BY tb_foro.fecha_creacion DESC");

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql.toString());

            // Inicializa el índice de parámetro para el PreparedStatement.
            int index = 1;

            // Si el filtro de asignatura no es nulo ni vacío, se asigna al parámetro correspondiente en la consulta.
            if (filtro.getAsignaturaId() > 0) {
                stat.setInt(index++, filtro.getAsignaturaId());
            }

            // Si el filtro de idioma no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (filtro.getIdiomaId() > 0) {
                stat.setInt(index++, filtro.getIdiomaId());
            }

            // Si el filtro de tipo no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (filtro.getTipoId() > 0) {
                stat.setInt(index++, filtro.getTipoId());
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

    public ForoClass mostrarForoPorId(ForoClass foro) {
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
            stat.setInt(1, foro.getId());

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            if (rs.next()) {
                foro.setTitulo(rs.getString("tit_foro"));
                foro.setDescripcion(rs.getString("descrip_foro"));
                foro.setFecha(rs.getString("fecha_creacion"));
                foro.setIdioma(rs.getString("idioma"));
                foro.setIdiomaId(rs.getInt("id_idioma_fk"));
                foro.setAsignatura(rs.getString("asignatura"));
                foro.setAsignaturaId(rs.getInt("id_asig_fk"));
                foro.setTipo(rs.getString("tipo"));
                foro.setTipoId(rs.getInt("id_tpfr_fk"));
                foro.setNombreUsuario(rs.getString("nom_usua") + " " + rs.getString("ape_usua"));
                foro.setRolUsuario(rs.getString("nom_rol"));
                foro.setUsuarioDoc(rs.getInt("doc_usua_fk"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return foro;
    }

    public void editarForo(ForoClass foro) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_foro SET tit_foro= ?, descrip_foro = ? WHERE id_foro = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, foro.getTitulo());
            stat.setString(2, foro.getDescripcion());
            stat.setInt(3, foro.getId());

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public void eliminarForo(ForoClass foro) throws SQLException {
        Connection conex = null;
        PreparedStatement statRespuestas = null;
        PreparedStatement statForo = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false); // Desactivar el autocommit para manejar la transacción manualmente

            // Eliminar respuestas asociadas al foro
            String deleteRespuestas = "DELETE FROM tb_respuesta_foro WHERE id_foro_fk = ?";
            statRespuestas = conex.prepareStatement(deleteRespuestas);
            statRespuestas.setInt(1, foro.getId());
            statRespuestas.executeUpdate();

            // Eliminar el foro
            String deleteForo = "DELETE FROM tb_foro WHERE id_foro = ?";
            statForo = conex.prepareStatement(deleteForo);
            statForo.setInt(1, foro.getId());
            statForo.executeUpdate();

            // Commit de la transacción
            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                conex.rollback(); // Deshacer la transacción en caso de error
            }
            e.printStackTrace();
            throw e;
        } finally { // https://puntocomnoesunlenguaje.blogspot.com/2017/11/java-jdbc-transacciones.html
            conexion.close(conex, statRespuestas, null);
            conexion.close(conex, statForo, null);
        }
    }

}
