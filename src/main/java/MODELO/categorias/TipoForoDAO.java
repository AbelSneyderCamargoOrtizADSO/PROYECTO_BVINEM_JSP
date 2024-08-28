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
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
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
    * Este método inserta un nuevo registro en la tabla `tb_tipo_foro`, utilizando los datos
    * proporcionados en el objeto {@link TipoForoClass}. El único campo que se inserta es el nombre
    * del tipo de foro.
    * 
    * @param tipoforo El objeto {@link TipoForoClass} que contiene los datos del tipo de foro.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void agregarTipoForo(TipoForoClass tipoforo) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar un nuevo tipo de foro en la base de datos
            String query = "INSERT INTO tb_tipo_foro (nom_tp_foro) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipoforo.getNombre()); // Establece el nombre del tipo de foro

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
    * Método para editar un tipo de foro existente en la base de datos.
    * 
    * Este método actualiza los datos de un tipo de foro en la tabla `tb_tipo_foro`, utilizando
    * el ID del tipo de foro para identificar cuál se debe actualizar. El único campo que se
    * puede modificar es el nombre del tipo de foro.
    * 
    * @param tipoforo El objeto {@link TipoForoClass} que contiene los datos actualizados del tipo de foro.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarTipoForo(TipoForoClass tipoforo) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el nombre de un tipo de foro existente en la base de datos
            String query = "UPDATE tb_tipo_foro SET nom_tp_foro = ? WHERE id_tp_foro = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, tipoforo.getNombre()); // Establece el nuevo nombre del tipo de foro
            stat.setInt(2, tipoforo.getId()); // Establece el ID del tipo de foro que se va a actualizar

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
        Connection conex = null; // Declaración de la variable para la conexión a la base de datos, inicialmente null
        PreparedStatement statUsuario = null; // Declaración de la variable para la declaración preparada, inicialmente null

        try {
            conex = conexion.Conexion(); // Obtener la conexión a la base de datos utilizando el método Conexion de la clase conexion
            String query = "UPDATE tb_tipo_foro SET estado = ? WHERE id_tp_foro = ?"; // Definir la consulta SQL para actualizar el estado del tipo de foro
            statUsuario = conex.prepareStatement(query); // Preparar la declaración SQL con la consulta definida
            statUsuario.setBoolean(1, estado); // Asignar el valor booleano 'estado' al primer parámetro de la consulta
            statUsuario.setInt(2, tipoforo.getId()); // Asignar el ID del tipo de foro al segundo parámetro de la consulta
            statUsuario.executeUpdate(); // Ejecutar la actualización en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la traza del error en caso de que ocurra una excepción SQL
            throw e; // Relanzar la excepción para que sea manejada por el método que llama
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
