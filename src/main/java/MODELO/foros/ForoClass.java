/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 * Clase que representa un foro con sus propiedades y métodos. Contiene la
 * información relevante de un foro, como su título, descripción, fecha de
 * creación, idioma, asignatura, tipo, y más.
 *
 * @author Abel Camargo
 * @see <a href="https://openwebinars.net/blog/introduccion-a-poo-en-java-objetos-y-clases/">Introducción a POO en Java</a>
 */
public class ForoClass {

    private int id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private int idiomaId;
    private String idioma;
    private String asignatura;
    private int asignaturaId;
    private String tipo;
    private int tipoId;
    private String nombreUsuario;
    private String rolUsuario;
    private int usuarioDoc;

    /**
     * Constructor por defecto.
     */
    public ForoClass() {

    }

    /**
     * Constructor con parámetros para inicializar un foro con datos
     * específicos.
     *
     * @param id El ID del foro.
     * @param titulo El título del foro.
     * @param descripcion La descripción del foro.
     * @param fecha La fecha de creación del foro.
     * @param idioma El nombre del idioma del foro.
     * @param asignatura El nombre de la asignatura del foro.
     * @param tipo El tipo de foro.
     */
    public ForoClass(int id, String titulo, String descripcion, String fecha, String idioma, String asignatura, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idioma = idioma;
        this.asignatura = asignatura;
        this.tipo = tipo;
    }

    /**
     * Obtiene el ID del foro.
     *
     * @return El ID del foro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del foro.
     *
     * @param id El nuevo ID del foro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del foro.
     *
     * @return El título del foro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del foro.
     *
     * @param titulo El título que se va a asignar al foro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción del foro.
     *
     * @return La descripción del foro.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del foro.
     *
     * @param descripcion La descripción que se va a asignar al foro.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene una versión resumida de la descripción del foro, eliminando
     * etiquetas HTML y limitando a 200 caracteres.
     *
     * @return La descripción resumida del foro.
     */
    public String getDescripcionResumida() {
        // Reemplazar etiquetas en bloque por un espacio
        String textoConEspacios = descripcion.replaceAll("(?i)<(p|div|h[1-6])[^>]*>", " ")
                .replaceAll("(?i)</(p|div|h[1-6])>", " ");
        // Eliminar otras etiquetas HTML usando una expresión regular
        String textoPlano = textoConEspacios.replaceAll("<[^>]*>", "").trim();
        // Limitar el texto a 200 caracteres
        if (textoPlano.length() > 200) {
            textoPlano = textoPlano.substring(0, 200) + "...";
        }
        return textoPlano;
    }

    /**
     * Obtiene la fecha de creación del foro.
     *
     * @return La fecha de creación del foro.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de creación del foro.
     *
     * @param fecha La fecha de creación que se va a asignar al foro.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el idioma en el que está el foro.
     *
     * @return El idioma del foro.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma del foro.
     *
     * @param idioma El idioma que se va a asignar al foro.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene el ID del idioma en el que está el foro.
     *
     * @return El ID del idioma del foro.
     */
    public int getIdiomaId() {
        return idiomaId;
    }

    /**
     * Establece el ID del idioma del foro.
     *
     * @param idiomaId El ID del idioma que se va a asignar al foro.
     */
    public void setIdiomaId(int idiomaId) {
        this.idiomaId = idiomaId;
    }

    /**
     * Obtiene la asignatura a la que pertenece el foro.
     *
     * @return La asignatura del foro.
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * Establece la asignatura del foro.
     *
     * @param asignatura La asignatura que se va a asignar al foro.
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * Obtiene el ID de la asignatura a la que pertenece el foro.
     *
     * @return El ID de la asignatura del foro.
     */
    public int getAsignaturaId() {
        return asignaturaId;
    }

    /**
     * Establece el ID de la asignatura del foro.
     *
     * @param asignaturaId El ID de la asignatura que se va a asignar al foro.
     */
    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    /**
     * Obtiene el tipo de foro.
     *
     * @return El tipo de foro.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de foro.
     *
     * @param tipo El tipo que se va a asignar al foro.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el ID del tipo de foro.
     *
     * @return El ID del tipo de foro.
     */
    public int getTipoId() {
        return tipoId;
    }

    /**
     * Establece el ID del tipo de foro.
     *
     * @param tipoId El ID del tipo de foro que se va a asignar.
     */
    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * Obtiene el nombre del usuario que creó el foro.
     *
     * @return El nombre del usuario que creó el foro.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre del usuario que creó el foro.
     *
     * @param nombreUsuario El nombre del usuario que se va a asignar al foro.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el rol del usuario que creó el foro.
     *
     * @return El rol del usuario que creó el foro.
     */
    public String getRolUsuario() {
        return rolUsuario;
    }

    /**
     * Establece el rol del usuario que creó el foro.
     *
     * @param rolUsuario El rol del usuario que se va a asignar al foro.
     */
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * Obtiene el documento del usuario que creó el foro.
     *
     * @return El documento del usuario que creó el foro.
     */
    public int getUsuarioDoc() {
        return usuarioDoc;
    }

    /**
     * Establece el documento del usuario que creó el foro.
     *
     * @param usuarioDoc El documento del usuario que se va a asignar al foro.
     */
    public void setUsuarioDoc(int usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }

}
