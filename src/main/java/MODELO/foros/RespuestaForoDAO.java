/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

import MODELO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de la base de datos relacionadas con las respuestas de los foros.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @author Abel Camargo
 * @see Conexion
 * @see RespuestaClass
 * @see ForoClass
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */

public class RespuestaForoDAO {
    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public RespuestaForoDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para subir una nueva respuesta al foro.
    * 
    * Este método inserta una nueva respuesta en la tabla `tb_respuesta_foro` de la base de datos,
    * estableciendo el contenido de la respuesta, la fecha de publicación actual, el ID del foro al 
    * que pertenece la respuesta, y el ID del usuario que publica la respuesta.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que contiene los datos de la respuesta.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void subirRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar una nueva respuesta en el foro
            String query = "INSERT INTO tb_respuesta_foro(contenido, fecha_public, id_foro_fk, doc_usua_fk) VALUES(?,NOW(),?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido()); // Establece el contenido de la respuesta
            stat.setInt(2, respuesta.getIdForo()); // Establece el ID del foro al que pertenece la respuesta
            stat.setInt(3, respuesta.getUsuarioId()); // Establece el ID del usuario que publica la respuesta

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
     * Método para obtener una lista de respuestas de un foro específico.
     * 
     * @param foro El objeto {@link ForoClass} que representa el foro.
     * @return Una lista de objetos {@link RespuestaClass} que contiene las respuestas del foro.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<RespuestaClass> mostrarRespuestasPorForo(ForoClass foro) throws SQLException {
        // Crea una nueva lista de objetos RespuestaClass utilizando ArrayList para almacenar las respuestas del foro.
        List<RespuestaClass> respuestas = new ArrayList<>(); 
        Connection conex = null; // Declaración de la variable para la conexión a la base de datos, inicialmente null
        PreparedStatement stat = null; // Declaración de la variable para la declaración preparada, inicialmente null
        ResultSet rs = null; // Declaración de la variable para el conjunto de resultados, inicialmente null

        try {
            conex = conexion.Conexion(); // Establecer la conexión a la base de datos

            // Definir la consulta SQL para obtener las respuestas de un foro específico, incluyendo información de usuario y rol
            String query = "SELECT tb_respuesta_foro.*, tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_rol.nom_rol "
                         + "FROM tb_respuesta_foro "
                         + "JOIN tb_usuarios ON tb_respuesta_foro.doc_usua_fk = tb_usuarios.doc_usua "
                         + "JOIN tb_rol ON tb_usuarios.id_rol_fk = tb_rol.id_rol "
                         + "WHERE tb_respuesta_foro.id_foro_fk = ? "
                         + "ORDER BY tb_respuesta_foro.fecha_public DESC";

            stat = conex.prepareStatement(query); // Preparar la declaración SQL con la consulta definida
            stat.setInt(1, foro.getId()); // Asignar el ID del foro al primer parámetro de la consulta

            rs = stat.executeQuery(); // Ejecutar la consulta y obtener el conjunto de resultados

            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                int idRespu = rs.getInt("id_respu"); // Obtener el ID de la respuesta
                String contenido = rs.getString("contenido"); // Obtener el contenido de la respuesta
                String fechaPublic = rs.getString("fecha_public"); // Obtener la fecha de publicación de la respuesta
                String nombreUsuario = rs.getString("nom_usua") + " " + rs.getString("ape_usua"); // Obtener el nombre completo del usuario que publicó la respuesta
                String rolUsuario = rs.getString("nom_rol"); // Obtener el rol del usuario
                int usuarioId = rs.getInt("doc_usua_fk"); // Obtener el ID del usuario

                // Crear una nueva instancia del objeto RespuestaClass con los datos obtenidos de la base de datos
                RespuestaClass respuesta = new RespuestaClass(idRespu, contenido, fechaPublic, nombreUsuario, rolUsuario, usuarioId);
                respuestas.add(respuesta); // Añadir la respuesta creada a la lista de respuestas
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la traza del error en caso de que ocurra una excepción SQL
            throw e; // Relanzar la excepción para que sea manejada por el método que llama
        } finally {
            // Cerrar los recursos de la base de datos para evitar fugas de memoria
            conexion.close(conex, stat, rs);
        }

        return respuestas; // Retornar la lista de respuestas obtenidas
    }

    
    /**
    * Método para editar una respuesta existente en el foro.
    * 
    * Este método actualiza el contenido de una respuesta existente en la tabla `tb_respuesta_foro` 
    * de la base de datos, utilizando el ID de la respuesta para identificar cuál se debe actualizar.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que contiene los datos actualizados de la respuesta.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el contenido de una respuesta en el foro
            String query = "UPDATE tb_respuesta_foro SET contenido = ? WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, respuesta.getContenido()); // Establece el nuevo contenido de la respuesta
            stat.setInt(2, respuesta.getId()); // Establece el ID de la respuesta a actualizar

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
    * Método para eliminar una respuesta del foro.
    * 
    * Este método elimina una respuesta existente de la tabla `tb_respuesta_foro` de la base de datos,
    * utilizando el ID de la respuesta para identificar cuál se debe eliminar.
    * 
    * @param respuesta El objeto {@link RespuestaClass} que representa la respuesta a eliminar.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void eliminarRespuesta(RespuestaClass respuesta) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para eliminar una respuesta del foro
            String query = "DELETE FROM tb_respuesta_foro WHERE id_respu = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, respuesta.getId()); // Establece el ID de la respuesta a eliminar

            stat.executeUpdate(); // Ejecuta la eliminación en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
            throw e; // Relanza la excepción para que sea manejada en un nivel superior
        } finally {
            // Cierra la conexión y la sentencia preparada
            conexion.close(conex, stat, null);
        }
    }

}
