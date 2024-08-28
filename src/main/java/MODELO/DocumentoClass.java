/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 * Clase que representa un documento. Contiene los atributos y métodos para
 * gestionar la información de un documento.
 * @see <a href="https://openwebinars.net/blog/introduccion-a-poo-en-java-objetos-y-clases/">Introducción a POO en Java</a>
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
     * Constructor por defecto. Permite crear una instancia de
     * {@link DocumentoClass} sin inicializar sus propiedades.
     */
    public DocumentoClass() {

    }

    /**
     * Constructor con parámetros para inicializar un documento con datos
     * específicos.
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

    /**
     * Obtiene el título del documento.
     *
     * @return El título del documento.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del documento.
     *
     * @param titulo El título que se va a asignar al documento.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del documento.
     *
     * @return El autor del documento.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del documento.
     *
     * @param autor El autor que se va a asignar al documento.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene la descripción del documento.
     *
     * @return La descripción del documento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del documento.
     *
     * @param descripcion La descripción que se va a asignar al documento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el año de publicación del documento.
     *
     * @return El año de publicación del documento.
     */
    public String getYear() {
        return year;
    }

    /**
     * Establece el año de publicación del documento.
     *
     * @param year El año de publicación que se va a asignar al documento.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Obtiene el ID del idioma del documento.
     *
     * @return El ID del idioma del documento.
     */
    public int getIdiomaId() {
        return idiomaId;
    }

    /**
     * Establece el ID del idioma del documento.
     *
     * @param idiomaId El ID del idioma que se va a asignar al documento.
     */
    public void setIdiomaId(int idiomaId) {
        this.idiomaId = idiomaId;
    }

    /**
     * Obtiene el nombre del idioma del documento.
     *
     * @return El nombre del idioma del documento.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el nombre del idioma del documento.
     *
     * @param idioma El nombre del idioma que se va a asignar al documento.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene el ID de la asignatura a la que pertenece el documento.
     *
     * @return El ID de la asignatura del documento.
     */
    public int getAsignaturaId() {
        return asignaturaId;
    }

    /**
     * Establece el ID de la asignatura a la que pertenece el documento.
     *
     * @param asignaturaId El ID de la asignatura que se va a asignar al
     * documento.
     */
    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    /**
     * Obtiene el nombre de la asignatura a la que pertenece el documento.
     *
     * @return El nombre de la asignatura del documento.
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * Establece el nombre de la asignatura a la que pertenece el documento.
     *
     * @param asignatura El nombre de la asignatura que se va a asignar al
     * documento.
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Obtiene el ID del tipo de documento.
     *
     * @return El ID del tipo de documento.
     */
    public int getTipoId() {
        return tipoId;
    }

    /**
     * Establece el ID del tipo de documento.
     *
     * @param tipoId El ID del tipo que se va a asignar al documento.
     */
    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * Obtiene el tipo de documento.
     *
     * @return El tipo de documento.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de documento.
     *
     * @param tipo El tipo que se va a asignar al documento.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la ruta de la miniatura del documento.
     *
     * @return La ruta de la miniatura del documento.
     */
    public String getMiniaturaPath() {
        return miniaturaPath;
    }

    /**
     * Establece la ruta de la miniatura del documento.
     *
     * @param miniaturaPath La ruta de la miniatura que se va a asignar al
     * documento.
     */
    public void setMiniaturaPath(String miniaturaPath) {
        this.miniaturaPath = miniaturaPath;
    }

    /**
     * Obtiene la ruta del archivo PDF del documento.
     *
     * @return La ruta del archivo PDF del documento.
     */
    public String getArchivoPDF() {
        return archivoPDF;
    }

    /**
     * Establece la ruta del archivo PDF del documento.
     *
     * @param archivoPDF La ruta del archivo PDF que se va a asignar al
     * documento.
     */
    public void setArchivoPDF(String archivoPDF) {
        this.archivoPDF = archivoPDF;
    }

    /**
     * Obtiene el ID del usuario docente que subió el documento.
     *
     * @return El ID del usuario docente que subió el documento.
     */
    public int getUserDoc() {
        return userDoc;
    }

    /**
     * Establece el ID del usuario docente que subió el documento.
     *
     * @param userDoc El ID del usuario docente que se va a asignar al
     * documento.
     */
    public void setUserDoc(int userDoc) {
        this.userDoc = userDoc;
    }

}
