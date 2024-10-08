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
 * Clase que maneja las operaciones de la base de datos relacionadas con los foros.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see ForoClass
 * 
 * @author Abel Camargo
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */
public class ForoDAO {
    
    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public ForoDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para subir un nuevo foro a la base de datos.
    * 
    * Este método inserta un nuevo registro en la tabla `tb_foro` de la base de datos, utilizando
    * los datos proporcionados en el objeto {@link ForoClass}. Los campos incluyen el título del foro,
    * la descripción, la fecha de creación (establecida automáticamente como la fecha y hora actual),
    * el ID de la asignatura, el ID del usuario que crea el foro, el ID del idioma y el ID del tipo de foro.
    * 
    * @param foro El objeto {@link ForoClass} que contiene los datos del foro.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void subirForo(ForoClass foro) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar un nuevo foro en la base de datos
            String query = "INSERT INTO tb_foro(tit_foro, descrip_foro, fecha_creacion, id_asig_fk, doc_usua_fk, id_idioma_fk, id_tpfr_fk) VALUES(?,?,NOW(),?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, foro.getTitulo()); // Establece el título del foro
            stat.setString(2, foro.getDescripcion()); // Establece la descripción del foro
            stat.setInt(3, foro.getAsignaturaId()); // Establece el ID de la asignatura
            stat.setInt(4, foro.getUsuarioDoc()); // Establece el ID del usuario que crea el foro
            stat.setInt(5, foro.getIdiomaId()); // Establece el ID del idioma del foro
            stat.setInt(6, foro.getTipoId()); // Establece el ID del tipo de foro

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
     * Método para listar todos los foros.
     * 
     * @return Una lista de objetos {@link ForoClass} que contiene todos los foros.
     */
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
    
    /**
     * Método para filtrar foros según ciertos criterios.
     * 
     * @param filtro El objeto {@link ForoClass} que contiene los criterios de filtrado.
     * @return Una lista de objetos {@link ForoClass} que cumplen con los criterios de filtrado.
     */
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
                // El WHERE 1=1 se usa para simplificar la construcción dinámica de la consulta SQL
                // Añade una condición siempre verdadera que facilita agregar condiciones adicionales
                // sin preocuparse por la sintaxis del operador lógico AND al inicio de la condición
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
    
    /**
    * Método para mostrar un foro específico por su ID.
    * 
    * Este método realiza una consulta en la base de datos para obtener los datos completos
    * de un foro específico, utilizando el ID proporcionado en el objeto {@link ForoClass}.
    * La información recuperada incluye el título, descripción, fecha de creación, idioma,
    * asignatura, tipo de foro, y los datos del usuario que creó el foro.
    * 
    * @param foro El objeto {@link ForoClass} que contiene el ID del foro a buscar.
    * @return El objeto {@link ForoClass} con los datos del foro encontrado.
    */
    public ForoClass mostrarForoPorId(ForoClass foro) {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada
        ResultSet rs = null; // ResultSet para almacenar los resultados de la consulta

        // Consulta SQL para obtener los datos del foro y sus relaciones con otras tablas
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

            // Si se encuentra un registro, se asignan los valores a los atributos del objeto foro
            if (rs.next()) {
                foro.setTitulo(rs.getString("tit_foro")); // Establece el título del foro
                foro.setDescripcion(rs.getString("descrip_foro")); // Establece la descripción del foro
                foro.setFecha(rs.getString("fecha_creacion")); // Establece la fecha de creación del foro
                foro.setIdioma(rs.getString("idioma")); // Establece el idioma del foro
                foro.setIdiomaId(rs.getInt("id_idioma_fk")); // Establece el ID del idioma
                foro.setAsignatura(rs.getString("asignatura")); // Establece la asignatura del foro
                foro.setAsignaturaId(rs.getInt("id_asig_fk")); // Establece el ID de la asignatura
                foro.setTipo(rs.getString("tipo")); // Establece el tipo de foro
                foro.setTipoId(rs.getInt("id_tpfr_fk")); // Establece el ID del tipo de foro
                foro.setNombreUsuario(rs.getString("nom_usua") + " " + rs.getString("ape_usua")); // Establece el nombre completo del usuario
                foro.setRolUsuario(rs.getString("nom_rol")); // Establece el rol del usuario
                foro.setUsuarioDoc(rs.getInt("doc_usua_fk")); // Establece el documento del usuario que creó el foro
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
        } finally {
            // Cierra la conexión, la sentencia preparada y el ResultSet
            conexion.close(conex, stat, rs);
        }

        return foro; // Retorna el objeto foro con los datos recuperados
    }

    
    /**
    * Método para editar un foro existente.
    * 
    * Este método actualiza los datos de un foro en la base de datos, utilizando el ID del foro
    * para identificar cuál se debe actualizar. Los campos que se pueden modificar incluyen
    * el título y la descripción del foro.
    * 
    * @param foro El objeto {@link ForoClass} que contiene los datos actualizados del foro.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarForo(ForoClass foro) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar el título y la descripción del foro
            String query = "UPDATE tb_foro SET tit_foro= ?, descrip_foro = ? WHERE id_foro = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, foro.getTitulo()); // Establece el nuevo título del foro
            stat.setString(2, foro.getDescripcion()); // Establece la nueva descripción del foro
            stat.setInt(3, foro.getId()); // Establece el ID del foro que se va a actualizar

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
     * Método para eliminar un foro y sus respuestas asociadas.
     * 
     * @param foro El objeto {@link ForoClass} que representa el foro a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     * @see <a href="https://puntocomnoesunlenguaje.blogspot.com/2017/11/java-jdbc-transacciones.html">Transacciones en Java JDBC</a>
     */
    public void eliminarForo(ForoClass foro) throws SQLException {
        Connection conex = null;
        PreparedStatement statRespuestas = null;
        PreparedStatement statForo = null;

        try {
            conex = conexion.Conexion();
            // La transaccion sirve para ejecutar en bloque varias sentencias relacionadas, manteniendo principios, tratando las sentancias como un unico bloque, si una sale mal la otra falla
            // Desactivar el autocommit para manejar la transacción manualmente.
            // Esto permite ejecutar varias sentencias SQL como una sola transacción.
            // Si una sentencia falla, se pueden revertir todas las operaciones de la transacción.
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

            // Confirmar la transacción.
            // Esto asegura que todas las operaciones realizadas en la transacción se hagan efectivas en la base de datos.
            // CONFIRMA CAMBIOS
            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                // Revertir la transacción en caso de error.
                // Esto deshace todas las operaciones realizadas en la transacción actual.
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, statRespuestas, null);
            conexion.close(conex, statForo, null);
        }
    }

}
