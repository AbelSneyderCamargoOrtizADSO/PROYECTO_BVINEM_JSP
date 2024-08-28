/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import MODELO.foros.ForoClass;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las búsquedas de documentos y foros en la base de datos.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de
 * datos.
 *
 * @see Conexion
 * @see DocumentoClass
 * @see ForoClass
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */
public class BusquedaClass {

    private Conexion conexion;

    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public BusquedaClass() {
        this.conexion = new Conexion();
    }

    /**
     * Método para buscar documentos en la base de datos.
     *
     * Este método realiza una consulta en la tabla `tb_documento` para
     * encontrar documentos cuyo título coincida parcial o totalmente con la
     * cadena de búsqueda proporcionada. Los resultados se ordenan por fecha de
     * carga en orden descendente.
     *
     * @param query La cadena de búsqueda para filtrar documentos por título.
     * @return Una lista de objetos {@link DocumentoClass} que coincide con la
     * búsqueda.
     */
    public List<DocumentoClass> buscarDocumentos(String query) {
        List<DocumentoClass> documentos = new ArrayList<>(); // Crear una lista vacía para almacenar los documentos que se obtendrán de la base de datos
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        // Consulta SQL para buscar documentos por título
        String sql = "SELECT tb_documento.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_doc.nom_tipo AS tipo "
                + "FROM tb_documento "
                + "JOIN tb_idiomas ON tb_documento.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_documento.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_doc ON tb_documento.id_tipo_fk = tb_tipo_doc.id_tipo "
                + "WHERE tb_documento.titulo LIKE ? " // Filtrar por coincidencia parcial en el título
                + "ORDER BY tb_documento.fecha_carga DESC"; // Ordenar resultados por fecha de carga en orden descendente

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql);
            stat.setString(1, "%" + query + "%"); // Incluir la cadena de búsqueda en la consulta
            rs = stat.executeQuery();

            // Recorrer los resultados y crear objetos DocumentoClass
            while (rs.next()) {
                int id = rs.getInt("id_doc");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String descripcion = rs.getString("descripcion");
                String year = rs.getString("year_publi");
                String idioma = rs.getString("idioma");
                String asignatura = rs.getString("asignatura");
                String tipo = rs.getString("tipo");
                String miniatura = rs.getString("miniatura");
                int userDoc = rs.getInt("doc_docente_fk");
                String archivoPDF = rs.getString("archivo");

                // Agregar el documento a la lista
                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idioma, asignatura, tipo, miniatura, userDoc, archivoPDF));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la excepción en caso de error
        } finally {
            conexion.close(conex, stat, rs); // Cerrar los recursos
        }

        return documentos; // Retornar la lista de documentos encontrados
    }

    /**
     * Método para buscar foros en la base de datos.
     *
     * Este método realiza una consulta en la tabla `tb_foro` para encontrar
     * foros cuyo título coincida parcial o totalmente con la cadena de búsqueda
     * proporcionada. Los resultados se ordenan por fecha de creación en orden
     * descendente.
     *
     * @param query La cadena de búsqueda para filtrar foros por título.
     * @return Una lista de objetos {@link ForoClass} que coincide con la
     * búsqueda.
     */
    public List<ForoClass> buscarForos(String query) {
        List<ForoClass> foros = new ArrayList<>(); // Crear una lista vacía para almacenar los foros que se obtendrán de la base de datos
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        // Consulta SQL para buscar foros por título
        String sql = "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "WHERE tb_foro.tit_foro LIKE ? " // Filtrar por coincidencia parcial en el título
                + "ORDER BY tb_foro.fecha_creacion DESC"; // Ordenar resultados por fecha de creación en orden descendente

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql);
            stat.setString(1, "%" + query + "%"); // Incluir la cadena de búsqueda en la consulta
            rs = stat.executeQuery();

            // Recorrer los resultados y crear objetos ForoClass
            while (rs.next()) {
                int id = rs.getInt("id_foro");
                String titulo = rs.getString("tit_foro");
                String descripcion = rs.getString("descrip_foro");
                String fecha = rs.getString("fecha_creacion");
                String idioma = rs.getString("idioma");
                String asignatura = rs.getString("asignatura");
                String tipo = rs.getString("tipo");

                // Agregar el foro a la lista
                foros.add(new ForoClass(id, titulo, descripcion, fecha, idioma, asignatura, tipo));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la excepción en caso de error
        } finally {
            conexion.close(conex, stat, rs); // Cerrar los recursos
        }

        return foros; // Retornar la lista de foros encontrados
    }

}
