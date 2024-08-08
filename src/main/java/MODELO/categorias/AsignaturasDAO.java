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
 * Clase que maneja las operaciones de la base de datos relacionadas con las asignaturas.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see AsignaturaClass
 */
public class AsignaturasDAO {

    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public AsignaturasDAO() {
        this.conexion = new Conexion();
    }
    
    /**
     * Método para agregar una nueva asignatura a la base de datos.
     * 
     * @param asignatura El objeto {@link AsignaturaClass} que contiene los datos de la asignatura.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void agregarAsignatura(AsignaturaClass asignatura) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "INSERT INTO tb_asignaturas (nom_asig) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, asignatura.getNombre());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    /**
     * Método para editar una asignatura existente en la base de datos.
     * 
     * @param asignatura El objeto {@link AsignaturaClass} que contiene los datos actualizados de la asignatura.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void editarAsignatura(AsignaturaClass asignatura) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_asignaturas SET nom_asig = ? WHERE id_asig = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, asignatura.getNombre());
            stat.setInt(2, asignatura.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    /**
     * Método para obtener todas las asignaturas de la base de datos.
     * 
     * @return Una lista de objetos {@link AsignaturaClass} que contiene todas las asignaturas.
     */
    // Método para obtener todas las asignaturas
    public List<AsignaturaClass> obtenerAsignaturas() {
        // Crear una lista vacía para almacenar las asignaturas que se obtendrán de la base de datos
        List<AsignaturaClass> asignaturas = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        // Definir la consulta SQL para obtener las asignaturas
        String sql = "SELECT * FROM tb_asignaturas";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                // Obtener el valor de la columna 'id_asig' del resultado actual
                int id = rs.getInt("id_asig");

                // Obtener el valor de la columna 'nom_asig' del resultado actual
                String nombre = rs.getString("nom_asig");
                boolean estado = rs.getBoolean("estado");

                // Crear un nuevo objeto AsignaturaClass usando los valores obtenidos y agregarlo a la lista
                asignaturas.add(new AsignaturaClass(id, nombre, estado));
            }
        } catch (Exception e) {
            // Capturar cualquier excepción que pueda ocurrir y imprimir el stack trace
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        // Devolver la lista de asignaturas obtenidas de la base de datos
        return asignaturas;
    }
    
    /**
     * Método para cambiar el estado de una asignatura en la base de datos.
     * 
     * @param asignatura El objeto {@link AsignaturaClass} que representa la asignatura a actualizar.
     * @param estado El nuevo estado de la asignatura.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void cambiarEstado(AsignaturaClass asignatura, boolean estado) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_asignaturas SET estado = ? WHERE id_asig = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setBoolean(1, estado);
            statUsuario.setInt(2, asignatura.getId());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Método para inhabilitar una asignatura en la base de datos.
     * 
     * @param asignatura El objeto {@link AsignaturaClass} que representa la asignatura a inhabilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void inhabilitarAsignatura(AsignaturaClass asignatura) throws SQLException {
        cambiarEstado(asignatura, false);
    }
    
    /**
     * Método para habilitar una asignatura en la base de datos.
     * 
     * @param asignatura El objeto {@link AsignaturaClass} que representa la asignatura a habilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void habilitarAsignatura(AsignaturaClass asignatura) throws SQLException {
        cambiarEstado(asignatura, true);
    }
}
