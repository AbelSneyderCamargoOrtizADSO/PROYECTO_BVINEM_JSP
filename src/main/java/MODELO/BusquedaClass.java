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
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see DocumentoClass
 * @see ForoClass
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
     * @param query La cadena de búsqueda para filtrar documentos por título.
     * @return Una lista de objetos {@link DocumentoClass} que coincide con la búsqueda.
     */
    public List<DocumentoClass> buscarDocumentos(String query) {
        List<DocumentoClass> documentos = new ArrayList<>(); // Crear una lista vacía para almacenar los documentos que se obtendrán de la base de datos
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT tb_documento.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_doc.nom_tipo AS tipo "
                + "FROM tb_documento "
                + "JOIN tb_idiomas ON tb_documento.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_documento.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_doc ON tb_documento.id_tipo_fk = tb_tipo_doc.id_tipo "
                + "WHERE tb_documento.titulo LIKE ? " // El LIKE en SQL se usa para encontrar filas donde el valor de una columna coincide parcialmente con un patrón. 
                + "ORDER BY tb_documento.fecha_carga DESC";

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql);
            stat.setString(1, "%" + query + "%");
            rs = stat.executeQuery();

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

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idioma, asignatura, tipo, miniatura, userDoc, archivoPDF)); // Crear un nuevo objeto DocumentoClass usando los valores obtenidos y agregarlo a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return documentos;
    }
    
    /**
     * Método para buscar foros en la base de datos.
     * 
     * @param query La cadena de búsqueda para filtrar foros por título.
     * @return Una lista de objetos {@link ForoClass} que coincide con la búsqueda.
     */
    public List<ForoClass> buscarForos(String query) {
        List<ForoClass> foros = new ArrayList<>();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT tb_foro.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_foro.nom_tp_foro AS tipo "
                + "FROM tb_foro "
                + "JOIN tb_idiomas ON tb_foro.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_foro.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_foro ON tb_foro.id_tpfr_fk = tb_tipo_foro.id_tp_foro "
                + "WHERE tb_foro.tit_foro LIKE ? "
                + "ORDER BY tb_foro.fecha_creacion DESC";

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql);
            stat.setString(1, "%" + query + "%");
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return foros;
    }
}
