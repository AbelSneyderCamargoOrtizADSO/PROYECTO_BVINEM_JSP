/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 * Clase que representa un documento.
 * Contiene los atributos y métodos para gestionar la información de un documento.
 */
public class DocumentoClass {

    private int id;
    private String titulo;
    private String autor;
    private String descripcion;
    private String year;
    private int idiomaId;
    private String idioma;
    private int asignaturaId;
    private String asignatura;
    private int tipoId;
    private String tipo;
    private String miniaturaPath;
    private String archivoPDF;
    private int userDoc;
    
    /**
     * Constructor por defecto.
     * Permite crear una instancia de {@link DocumentoClass} sin inicializar sus propiedades.
     */
    public DocumentoClass() {

    }
    
    /**
     * Constructor con parámetros para inicializar un documento con datos específicos.
     * 
     * @param id El ID del documento.
     * @param titulo El título del documento.
     * @param autor El autor del documento.
     * @param descripcion La descripción del documento.
     * @param year El año de publicación del documento.
     * @param idioma El idioma del documento.
     * @param asignatura La asignatura del documento.
     * @param tipo El tipo del documento.
     * @param miniaturaPath La ruta de la miniatura del documento.
     * @param userDoc El ID del usuario que subió el documento.
     * @param archivoPDF La ruta del archivo PDF del documento.
     */
    public DocumentoClass(int id, String titulo, String autor, String descripcion, String year, String idioma, String asignatura, String tipo, String miniaturaPath, int userDoc, String archivoPDF) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
        this.year = year;
        this.idioma = idioma;
        this.asignatura = asignatura;
        this.tipo = tipo;
        this.miniaturaPath = miniaturaPath;
        this.userDoc = userDoc;
        this.archivoPDF = archivoPDF;
    }
    
    /**
     * Obtiene el ID del documento.
     * 
     * @return El ID del documento.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el ID del documento.
     * 
     * @param id El nuevo ID del documento.
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public int getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(int idiomaId) {
        this.idiomaId = idiomaId;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    
    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getMiniaturaPath() {
        return miniaturaPath;
    }

    public void setMiniaturaPath(String miniaturaPath) {
        this.miniaturaPath = miniaturaPath;
    }
    
    public String getArchivoPDF() {
        return archivoPDF;
    }

    public void setArchivoPDF(String archivoPDF) {
        this.archivoPDF = archivoPDF;
    }
    
    public int getUserDoc() {
        return userDoc;
    }

    public void setUserDoc(int userDoc) {
        this.userDoc = userDoc;
    }

}
