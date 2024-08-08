/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 * Clase que representa una respuesta en un foro.
 * Contiene la información relevante de una respuesta, como su contenido,
 * fecha de publicación, usuario que la publicó, y más.
 * 
 * @author Abel Camargo
 */
public class RespuestaClass {

    private int id;
    private String contenido;
    private String fechaPublicacion;
    private int idForo;
    private String nombreUsuario;
    private String rolUsuario;
    private int usuarioId;
    
    /**
     * Constructor por defecto.
     */
    public RespuestaClass() {
        
    }
    
    /**
     * Constructor con parámetros para inicializar una respuesta con datos específicos.
     * 
     * @param id El ID de la respuesta.
     * @param contenido El contenido de la respuesta.
     * @param fechaPublicacion La fecha de publicación de la respuesta.
     * @param nombreUsuario El nombre del usuario que publicó la respuesta.
     * @param rolUsuario El rol del usuario que publicó la respuesta.
     * @param usuarioId El ID del usuario que publicó la respuesta.
     */
    public RespuestaClass(int id, String contenido, String fechaPublicacion, String nombreUsuario, String rolUsuario, int usuarioId) {
        this.id = id;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;
        this.usuarioId = usuarioId;
    }
    
    /**
     * Obtiene el ID de la respuesta.
     * 
     * @return El ID de la respuesta.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el ID de la respuesta.
     * 
     * @param id El nuevo ID de la respuesta.
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public int getIdForo() {
        return idForo;
    }

    public void setIdForo(int idForo) {
        this.idForo = idForo;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
