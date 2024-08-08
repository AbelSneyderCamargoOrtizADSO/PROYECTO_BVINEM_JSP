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
 * Clase que maneja las operaciones de la base de datos relacionadas con los tipos de documento.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see TipoClass
 */
public class TipoDocDAO {

    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public TipoDocDAO() {
        this.conexion = new Conexion();
    }
    
    /**
     * Método para agregar un nuevo tipo de documento a la base de datos.
     * 
     * @param tipodoc El objeto {@link TipoClass} que contiene los datos del tipo de documento.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
    /**
     * Método para editar un tipo de documento existente en la base de datos.
     * 
     * @param tipodoc El objeto {@link TipoClass} que contiene los datos actualizados del tipo de documento.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
     /**
     * Método para obtener todos los tipos de documento de la base de datos.
     * 
     * @return Una lista de objetos {@link TipoClass} que contiene todos los tipos de documento.
     */
    public List<TipoClass> obtenerTipos() {
        List<TipoClass> tipos = new ArrayList<>(); // Crea una nueva lista de objetos TipoClass utilizando ArrayList para almacenar los tipos de documento.

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

                tipos.add(new TipoClass(id, nombre, estado)); // Añade el objeto TipoClass creado a la lista de tipos de documento.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tipos;
    }
    
    /**
     * Método para cambiar el estado de un tipo de documento en la base de datos.
     * 
     * @param tipodoc El objeto {@link TipoClass} que representa el tipo de documento a actualizar.
     * @param estado El nuevo estado del tipo de documento.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
    
    /**
     * Método para inhabilitar un tipo de documento en la base de datos.
     * 
     * @param tipodoc El objeto {@link TipoClass} que representa el tipo de documento a inhabilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void inhabilitarTipoDoc(TipoClass tipodoc) throws SQLException {
        cambiarEstado(tipodoc, false);
    }
    
    /**
     * Método para habilitar un tipo de documento en la base de datos.
     * 
     * @param tipodoc El objeto {@link TipoClass} que representa el tipo de documento a habilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void habilitarTipoDoc(TipoClass tipodoc) throws SQLException {
        cambiarEstado(tipodoc, true);
    }
}
