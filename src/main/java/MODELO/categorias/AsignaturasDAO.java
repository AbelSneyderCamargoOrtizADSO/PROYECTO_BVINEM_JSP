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
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
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
    * Este método inserta un nuevo registro en la tabla `tb_asignaturas`, utilizando los datos
    * proporcionados en el objeto {@link AsignaturaClass}. El único campo que se inserta es el nombre
    * de la asignatura.
    * 
    * @param asignatura El objeto {@link AsignaturaClass} que contiene los datos de la asignatura.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void agregarAsignatura(AsignaturaClass asignatura) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar una nueva asignatura en la base de datos
            String query = "INSERT INTO tb_asignaturas (nom_asig) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, asignatura.getNombre()); // Establece el nombre de la asignatura

            stat.executeUpdate(); // Ejecuta la inserción en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, stat, null);
        }
    }

   /**
    * Método para editar una asignatura existente en la base de datos.
    * 
    * Este método actualiza los datos de una asignatura en la tabla `tb_asignaturas`, utilizando
    * el ID de la asignatura para identificar cuál se debe actualizar. El único campo que se
    * puede modificar es el nombre de la asignatura.
    * 
    * @param asignatura El objeto {@link AsignaturaClass} que contiene los datos actualizados de la asignatura.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarAsignatura(AsignaturaClass asignatura) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el nombre de una asignatura existente en la base de datos
            String query = "UPDATE tb_asignaturas SET nom_asig = ? WHERE id_asig = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, asignatura.getNombre()); // Establece el nuevo nombre de la asignatura
            stat.setInt(2, asignatura.getId()); // Establece el ID de la asignatura que se va a actualizar

            stat.executeUpdate(); // Ejecuta la actualización en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
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
