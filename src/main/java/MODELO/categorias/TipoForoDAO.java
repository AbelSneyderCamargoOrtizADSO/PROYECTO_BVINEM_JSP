/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

import MODELO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class TipoForoDAO {

    private Conexion conexion;

    public TipoForoDAO() {
        this.conexion = new Conexion();
    }

    public void agregarTipoForo(TipoForoClass tipoforo) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "INSERT INTO tb_tipo_foro (nom_tp_foro) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipoforo.getNombre());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public void editarTipoForo(TipoForoClass tipoforo) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_tipo_foro SET nom_tp_foro = ? WHERE id_tp_foro = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipoforo.getNombre());
            stat.setInt(2, tipoforo.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public List<TipoForoClass> obtenerTiposForo() {
        List<TipoForoClass> tiposforo = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_tipo_foro";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tp_foro");
                String nombre = rs.getString("nom_tp_foro");
                boolean estado = rs.getBoolean("estado");

                tiposforo.add(new TipoForoClass(id, nombre, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tiposforo;
    }

    public void cambiarEstado(TipoForoClass tipoforo, boolean estado) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_tipo_foro SET estado = ? WHERE id_tp_foro = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setBoolean(1, estado);
            statUsuario.setInt(2, tipoforo.getId());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void inhabilitarTipoForo(TipoForoClass tipoforo) throws SQLException {
        cambiarEstado(tipoforo, false);
    }

    public void habilitarTipoForo(TipoForoClass tipoforo) throws SQLException {
        cambiarEstado(tipoforo, true);
    }
}
