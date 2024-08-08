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
 * Clase que maneja las operaciones de la base de datos relacionadas con los tipos de foro.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see TipoForoClass
 */
public class TipoForoDAO {

    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public TipoForoDAO() {
        this.conexion = new Conexion();
    }
    
    /**
     * Método para agregar un nuevo tipo de foro a la base de datos.
     * 
     * @param tipoforo El objeto {@link TipoForoClass} que contiene los datos del tipo de foro.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
    /**
     * Método para editar un tipo de foro existente en la base de datos.
     * 
     * @param tipoforo El objeto {@link TipoForoClass} que contiene los datos actualizados del tipo de foro.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
    /**
     * Método para obtener todos los tipos de foro de la base de datos.
     * 
     * @return Una lista de objetos {@link TipoForoClass} que contiene todos los tipos de foro.
     */
    public List<TipoForoClass> obtenerTiposForo() {
        List<TipoForoClass> tiposforo = new ArrayList<>(); // Crea una nueva lista de objetos TipoForoClass utilizando ArrayList para almacenar los tipos de foro.

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

                tiposforo.add(new TipoForoClass(id, nombre, estado)); // Añade el objeto TipoForoClass creado a la lista de tipos de foro.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tiposforo;
    }
    
    /**
     * Método para cambiar el estado de un tipo de foro en la base de datos.
     * 
     * @param tipoforo El objeto {@link TipoForoClass} que representa el tipo de foro a actualizar.
     * @param estado El nuevo estado del tipo de foro.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
    /**
     * Método para inhabilitar un tipo de foro en la base de datos.
     * 
     * @param tipoforo El objeto {@link TipoForoClass} que representa el tipo de foro a inhabilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void inhabilitarTipoForo(TipoForoClass tipoforo) throws SQLException {
        cambiarEstado(tipoforo, false);
    }

    /**
     * Método para habilitar un tipo de foro en la base de datos.
     * 
     * @param tipoforo El objeto {@link TipoForoClass} que representa el tipo de foro a habilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void habilitarTipoForo(TipoForoClass tipoforo) throws SQLException {
        cambiarEstado(tipoforo, true);
    }
}
