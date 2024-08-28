/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

import MODELO.Conexion;
import MODELO.usuarios.UsuarioClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de la base de datos relacionadas con los idiomas.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see IdiomaClass
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */

public class IdiomasDAO {

    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public IdiomasDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para agregar un nuevo idioma a la base de datos.
    * 
    * Este método inserta un nuevo registro en la tabla `tb_idiomas`, utilizando los datos
    * proporcionados en el objeto {@link IdiomaClass}. El único campo que se inserta es el nombre
    * del idioma.
    * 
    * @param idioma El objeto {@link IdiomaClass} que contiene los datos del idioma.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void agregarIdioma(IdiomaClass idioma) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar un nuevo idioma en la base de datos
            String query = "INSERT INTO tb_idiomas (nom_idioma) VALUES(?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, idioma.getNombre()); // Establece el nombre del idioma

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
    * Método para editar un idioma existente en la base de datos.
    * 
    * Este método actualiza los datos de un idioma en la tabla `tb_idiomas`, utilizando
    * el ID del idioma para identificar cuál se debe actualizar. El único campo que se
    * puede modificar es el nombre del idioma.
    * 
    * @param idioma El objeto {@link IdiomaClass} que contiene los datos actualizados del idioma.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarIdioma(IdiomaClass idioma) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el nombre de un idioma existente en la base de datos
            String query = "UPDATE tb_idiomas SET nom_idioma = ? WHERE id_idioma = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, idioma.getNombre()); // Establece el nuevo nombre del idioma
            stat.setInt(2, idioma.getId()); // Establece el ID del idioma que se va a actualizar

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
     * Método para obtener todos los idiomas de la base de datos.
     * 
     * @return Una lista de objetos {@link IdiomaClass} que contiene todos los idiomas.
     */
    public List<IdiomaClass> obtenerIdiomas() {
        List<IdiomaClass> idiomas = new ArrayList<>(); // Crea una nueva lista de objetos IdiomaClass utilizando ArrayList para almacenar los idiomas.

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_idiomas";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_idioma");
                String nombre = rs.getString("nom_idioma");
                boolean estado = rs.getBoolean("estado");

                idiomas.add(new IdiomaClass(id, nombre, estado)); // Añade el objeto IdiomaClass creado a la lista de idiomas.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return idiomas;
    }
    
    /**
     * Método para cambiar el estado de un idioma en la base de datos.
     * 
     * @param idioma El objeto {@link IdiomaClass} que representa el idioma a actualizar.
     * @param estado El nuevo estado del idioma.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void cambiarEstado(IdiomaClass idioma, boolean estado) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_idiomas SET estado = ? WHERE id_idioma = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setBoolean(1, estado);
            statUsuario.setInt(2, idioma.getId());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Método para inhabilitar un idioma en la base de datos.
     * 
     * @param idioma El objeto {@link IdiomaClass} que representa el idioma a inhabilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void inhabilitarIdioma(IdiomaClass idioma) throws SQLException {
        cambiarEstado(idioma, false);
    }
    
    /**
     * Método para habilitar un idioma en la base de datos.
     * 
     * @param idioma El objeto {@link IdiomaClass} que representa el idioma a habilitar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void habilitarIdioma(IdiomaClass idioma) throws SQLException {
        cambiarEstado(idioma, true);
    }
}
