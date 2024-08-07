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
public class TipoDocDAO {

    private Conexion conexion;

    public TipoDocDAO() {
        this.conexion = new Conexion();
    }

    public void agregarTipoDoc(TipoClass tipodoc) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "INSERT INTO tb_tipo_doc (nom_tipo) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipodoc.getNombre());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public void editarTipoDoc(TipoClass tipodoc) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_tipo_doc SET nom_tipo = ? WHERE id_tipo = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipodoc.getNombre());
            stat.setInt(2, tipodoc.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    public List<TipoClass> obtenerTipos() {
        List<TipoClass> tipos = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_tipo_doc";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tipo");
                String nombre = rs.getString("nom_tipo");
                boolean estado = rs.getBoolean("estado");

                tipos.add(new TipoClass(id, nombre, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tipos;
    }
    
    public void cambiarEstado(TipoClass tipodoc, boolean estado) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_tipo_doc SET estado = ? WHERE id_tipo = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setBoolean(1, estado);
            statUsuario.setInt(2, tipodoc.getId());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void inhabilitarTipoDoc(TipoClass tipodoc) throws SQLException {
        cambiarEstado(tipodoc, false);
    }

    public void habilitarTipoDoc(TipoClass tipodoc) throws SQLException {
        cambiarEstado(tipodoc, true);
    }
}
