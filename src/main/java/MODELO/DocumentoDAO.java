/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class DocumentoDAO {

    public void SubirDocumento(String tit, String autor, String descrip, String year_publi, int UserDoc, String asig, String idioma, String tipo, InputStream archivoPDF, String miniaturaPath) throws SQLException {

        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_documento(titulo, autor, descripcion, year_publi, fecha_carga, miniatura, link, doc_docente_fk, id_asig_fk, id_idioma_fk, id_tipo_fk) values(?,?,?,?,NOW(),?,?,?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tit);
            stat.setString(2, autor);
            stat.setString(3, descrip);
            stat.setInt(4, Integer.parseInt(year_publi));
            stat.setString(5, miniaturaPath);
            stat.setBlob(6, archivoPDF);
            stat.setInt(7, UserDoc);
            stat.setInt(8, Integer.parseInt(asig));
            stat.setInt(9, Integer.parseInt(idioma));
            stat.setInt(10, Integer.parseInt(tipo));

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

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

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

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idioma, asignatura, tipo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return documentos;
    }

    public byte[] MostrarDocumento(int id) {
        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT link FROM tb_documento WHERE id_doc = ?";
        byte[] pdfData = null;

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);
            stat.setInt(1, id);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            if (rs.next()) {
                pdfData = rs.getBytes("link");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return pdfData;
    }

    public List<DocumentoClass> FiltrarDocumentos(String asignatura, String idioma, String tipo) {
        List<DocumentoClass> documentos = new ArrayList<>();
        Conexion conexion = new Conexion();
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

        if (asignatura != null && !asignatura.isEmpty()) {
            sql.append("AND tb_documento.id_asig_fk = ? ");
        }
        if (idioma != null && !idioma.isEmpty()) {
            sql.append("AND tb_documento.id_idioma_fk = ? ");
        }
        if (tipo != null && !tipo.isEmpty()) {
            sql.append("AND tb_documento.id_tipo_fk = ? ");
        }

        sql.append("ORDER BY tb_documento.fecha_carga DESC");

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(sql.toString());

            // Inicializa el índice de parámetro para el PreparedStatement.
            int index = 1;

            // Si el filtro de asignatura no es nulo ni vacío, se asigna al parámetro correspondiente en la consulta.
            if (asignatura != null && !asignatura.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(asignatura));
            }
            // Si el filtro de idioma no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (idioma != null && !idioma.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(idioma));
            }
            // Si el filtro de tipo no es nulo ni vacío, se asigna al siguiente parámetro en la consulta.
            if (tipo != null && !tipo.isEmpty()) {
                stat.setInt(index++, Integer.parseInt(tipo));
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

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idiomaResult, asignaturaResult, tipoResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return documentos;
    }

}
