/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class DocumentoDAO {
    private Conexion conexion;
    
    public DocumentoDAO() {
        this.conexion = new Conexion();
    }

    public void SubirDocumento(DocumentoClass documento) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_documento(titulo, autor, descripcion, year_publi, fecha_carga, miniatura, archivo, doc_docente_fk, id_asig_fk, id_idioma_fk, id_tipo_fk) values(?,?,?,?,NOW(),?,?,?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, documento.getTitulo());
            stat.setString(2, documento.getAutor());
            stat.setString(3, documento.getDescripcion());
            stat.setInt(4, Integer.parseInt(documento.getYear()));
            stat.setString(5, documento.getMiniaturaPath());
            stat.setString(6, documento.getArchivoPDF());
            stat.setInt(7, documento.getUserDoc());
            stat.setInt(8, documento.getAsignaturaId());
            stat.setInt(9, documento.getIdiomaId());
            stat.setInt(10, documento.getAsignaturaId());

            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

    public List<DocumentoClass> ListarDocumentos() {
        List<DocumentoClass> documentos = new ArrayList<>();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT tb_documento.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_doc.nom_tipo AS tipo "
                + "FROM tb_documento "
                + "JOIN tb_idiomas ON tb_documento.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_documento.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_doc ON tb_documento.id_tipo_fk = tb_tipo_doc.id_tipo "
                + "ORDER BY tb_documento.fecha_carga DESC";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
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

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idioma, asignatura, tipo, miniatura, userDoc, archivoPDF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return documentos;
    }
    
    public List<DocumentoClass> FiltrarDocumentos(DocumentoClass documento) {
        List<DocumentoClass> documentos = new ArrayList<>();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder(
                "SELECT tb_documento.*, tb_idiomas.nom_idioma AS idioma, tb_asignaturas.nom_asig AS asignatura, tb_tipo_doc.nom_tipo AS tipo "
                + "FROM tb_documento "
                + "JOIN tb_idiomas ON tb_documento.id_idioma_fk = tb_idiomas.id_idioma "
                + "JOIN tb_asignaturas ON tb_documento.id_asig_fk = tb_asignaturas.id_asig "
                + "JOIN tb_tipo_doc ON tb_documento.id_tipo_fk = tb_tipo_doc.id_tipo "
                + "WHERE 1=1 " // El WHERE 1=1 sirve para simplificar la adición dinámica de condiciones, permitiendo concatenar condiciones adicionales con AND sin preocuparse por la posición de los separadores.
        );

        if (documento.getAsignaturaId() > 0) {
            sql.append("AND tb_documento.id_asig_fk = ? ");
        }
        if (documento.getIdiomaId() > 0) {
            sql.append("AND tb_documento.id_idioma_fk = ? ");
        }
        if (documento.getTipoId() > 0) {
            sql.append("AND tb_documento.id_tipo_fk = ? ");
        }

        sql.append("ORDER BY tb_documento.fecha_carga DESC");

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql.toString());

            // Inicializa el índice de parámetro para el PreparedStatement.
            int index = 1;

            // Si el filtro de asignatura no es nulo ni vacío, se asigna al parámetro correspondiente en la consulta.
            if (documento.getAsignaturaId() > 0) {
                stat.setInt(index++, documento.getAsignaturaId());
            }
            // Si el filtro de idioma no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (documento.getIdiomaId() > 0) {
                stat.setInt(index++, documento.getIdiomaId());
            }
            // Si el filtro de tipo no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (documento.getTipoId() > 0) {
                stat.setInt(index++, documento.getTipoId());
            }

            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_doc");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String descripcion = rs.getString("descripcion");
                String year = rs.getString("year_publi");
                String idiomaResult = rs.getString("idioma");
                String asignaturaResult = rs.getString("asignatura");
                String tipoResult = rs.getString("tipo");
                String miniatura = rs.getString("miniatura");
                int userDoc = rs.getInt("doc_docente_fk");
                String archivoPDF = rs.getString("archivo");

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idiomaResult, asignaturaResult, tipoResult, miniatura, userDoc, archivoPDF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return documentos;
    }
    
    public void editarDocumento(DocumentoClass documento) throws SQLException {
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "UPDATE tb_documento SET titulo = ?, autor = ?, descripcion = ?, year_publi = ? WHERE id_doc = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, documento.getTitulo());
            stat.setString(2, documento.getAutor());
            stat.setString(3, documento.getDescripcion());
            stat.setString(4, documento.getYear());
            stat.setInt(5, documento.getId());

            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
    
    public void eliminarDocumento(DocumentoClass documento) throws SQLException {
        
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "DELETE FROM tb_documento WHERE id_doc = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, documento.getId());
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }

}
