/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 *
 * @author Abelito
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
    
    public ForoClass() {
        
    }

    public ForoClass(int id, String titulo, String descripcion, String fecha, String idioma, String asignatura, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idioma = idioma;
        this.asignatura = asignatura;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

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
