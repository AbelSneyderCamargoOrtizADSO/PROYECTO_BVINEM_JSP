/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de la base de datos relacionadas con los documentos.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see DocumentoClass
 * @see <a href="https://www.arquitecturajava.com/dao-vs-repository-y-sus-diferencias/">Referencia - Introducción a POO en Java</a>
 * @see <a href="https://www.youtube.com/watch?v=tV9tvhrQGOg&t=1225s">Referencia - Crud en java</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html">Referencia - SQLException</a>
 */
public class DocumentoDAO {
    private Conexion conexion;
    
    /**
     * Constructor que inicializa el objeto de conexión.
     */
    public DocumentoDAO() {
        this.conexion = new Conexion();
    }
    
    /**
    * Método para subir un nuevo documento a la base de datos.
    * 
    * Este método inserta un nuevo registro en la tabla `tb_documento`, utilizando los datos
    * proporcionados en el objeto {@link DocumentoClass}. Los campos que se insertan incluyen
    * el título, autor, descripción, año de publicación, miniatura, archivo PDF, y las claves
    * foráneas correspondientes a la asignatura, idioma, tipo de documento, y el docente que
    * sube el documento.
    * 
    * @param documento El objeto {@link DocumentoClass} que contiene los datos del documento.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void SubirDocumento(DocumentoClass documento) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para insertar un nuevo documento en la base de datos
            String query = "INSERT INTO tb_documento(titulo, autor, descripcion, year_publi, fecha_carga, miniatura, archivo, doc_docente_fk, id_asig_fk, id_idioma_fk, id_tipo_fk) VALUES(?,?,?,?,NOW(),?,?,?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, documento.getTitulo()); // Establece el título del documento
            stat.setString(2, documento.getAutor()); // Establece el autor del documento
            stat.setString(3, documento.getDescripcion()); // Establece la descripción del documento
            stat.setInt(4, Integer.parseInt(documento.getYear())); // Establece el año de publicación del documento
            stat.setString(5, documento.getMiniaturaPath()); // Establece la ruta de la miniatura del documento
            stat.setString(6, documento.getArchivoPDF()); // Establece la ruta del archivo PDF
            stat.setInt(7, documento.getUserDoc()); // Establece el ID del docente que sube el documento
            stat.setInt(8, documento.getAsignaturaId()); // Establece el ID de la asignatura
            stat.setInt(9, documento.getIdiomaId()); // Establece el ID del idioma
            stat.setInt(10, documento.getTipoId()); // Establece el ID del tipo de documento

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
     * Método para listar todos los documentos de la base de datos.
     * 
     * @return Una lista de objetos {@link DocumentoClass} que contiene todos los documentos.
     */
    public List<DocumentoClass> ListarDocumentos() {
        List<DocumentoClass> documentos = new ArrayList<>(); // Crear una lista vacía para almacenar los documentos que se obtendrán de la base de datos

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

                documentos.add(new DocumentoClass(id, titulo, autor, descripcion, year, idioma, asignatura, tipo, miniatura, userDoc, archivoPDF)); // Crear un nuevo objeto DocumentoClass usando los valores obtenidos y agregarlo a la lista
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }
        // Devolver la lista de documentos obtenidos de la base de datos
        return documentos;
    }
    
    /**
     * Método para filtrar documentos en la base de datos.
     * 
     * @param documento El objeto {@link DocumentoClass} que contiene los criterios de filtrado.
     * @return Una lista de objetos {@link DocumentoClass} que coincide con los criterios de filtrado.
     */
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
                + "WHERE 1=1 " // El WHERE 1=1 se usa para simplificar la construcción dinámica de la consulta SQL
                                // Añade una condición siempre verdadera que facilita agregar condiciones adicionales
                                // sin preocuparse por la sintaxis del operador lógico AND al inicio de la condición
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
    
    /**
    * Método para editar un documento existente en la base de datos.
    * 
    * Este método actualiza los datos de un documento en la tabla `tb_documento`, utilizando
    * el ID del documento para identificar cuál se debe actualizar. Los campos que se pueden 
    * modificar incluyen el título, autor, descripción y año de publicación del documento.
    * 
    * @param documento El objeto {@link DocumentoClass} que contiene los datos actualizados del documento.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void editarDocumento(DocumentoClass documento) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para actualizar los campos del documento en la base de datos
            String query = "UPDATE tb_documento SET titulo = ?, autor = ?, descripcion = ?, year_publi = ? WHERE id_doc = ?";
            stat = conex.prepareStatement(query);
            stat.setString(1, documento.getTitulo()); // Establece el nuevo título del documento
            stat.setString(2, documento.getAutor()); // Establece el nuevo autor del documento
            stat.setString(3, documento.getDescripcion()); // Establece la nueva descripción del documento
            stat.setString(4, documento.getYear()); // Establece el nuevo año de publicación del documento
            stat.setInt(5, documento.getId()); // Establece el ID del documento que se va a actualizar

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
    * Método para eliminar un documento de la base de datos.
    * 
    * Este método elimina un documento de la tabla `tb_documento` en la base de datos,
    * utilizando el ID del documento para identificar cuál se debe eliminar.
    * 
    * @param documento El objeto {@link DocumentoClass} que representa el documento a eliminar.
    * @throws SQLException Si ocurre un error al interactuar con la base de datos.
    */
    public void eliminarDocumento(DocumentoClass documento) throws SQLException {
        Connection conex = null; // Conexión a la base de datos
        PreparedStatement stat = null; // Sentencia SQL preparada

        try {
            // Establece la conexión con la base de datos
            conex = conexion.Conexion();

            // Consulta SQL para eliminar un documento de la base de datos
            String query = "DELETE FROM tb_documento WHERE id_doc = ?";
            stat = conex.prepareStatement(query);
            stat.setInt(1, documento.getId()); // Establece el ID del documento que se va a eliminar

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
