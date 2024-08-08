/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 * Clase que representa un foro con sus propiedades y métodos.
 * Contiene la información relevante de un foro, como su título,
 * descripción, fecha de creación, idioma, asignatura, tipo, y más.
 * 
 * @author Abel Camargo
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
     * Constructor con parámetros para inicializar un foro con datos específicos.
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene una versión resumida de la descripción del foro, eliminando etiquetas HTML y limitando a 200 caracteres.
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(int idiomaId) {
        this.idiomaId = idiomaId;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }
    
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
    
    public int getUsuarioDoc() {
        return usuarioDoc;
    }
    
    public void setUsuarioDoc(int usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }
}
