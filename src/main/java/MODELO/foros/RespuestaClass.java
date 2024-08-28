/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 * Clase que representa una respuesta en un foro. Contiene la información
 * relevante de una respuesta, como su contenido, fecha de publicación, usuario
 * que la publicó, y más.
 *
 * @author Abel Camargo
 * @see <a href="https://openwebinars.net/blog/introduccion-a-poo-en-java-objetos-y-clases/">Introducción a POO en Java</a>
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
     * Constructor con parámetros para inicializar una respuesta con datos
     * específicos.
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

    /**
     * Obtiene el contenido de la respuesta.
     *
     * @return El contenido de la respuesta.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido de la respuesta.
     *
     * @param contenido El contenido que se va a asignar a la respuesta.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene la fecha de publicación de la respuesta.
     *
     * @return La fecha de publicación de la respuesta.
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación de la respuesta.
     *
     * @param fechaPublicacion La fecha de publicación que se va a asignar a la
     * respuesta.
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene el ID del foro al que pertenece la respuesta.
     *
     * @return El ID del foro.
     */
    public int getIdForo() {
        return idForo;
    }

    /**
     * Establece el ID del foro al que pertenece la respuesta.
     *
     * @param idForo El ID del foro que se va a asignar a la respuesta.
     */
    public void setIdForo(int idForo) {
        this.idForo = idForo;
    }

    /**
     * Obtiene el nombre del usuario que publicó la respuesta.
     *
     * @return El nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre del usuario que publicó la respuesta.
     *
     * @param nombreUsuario El nombre del usuario que se va a asignar a la
     * respuesta.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el rol del usuario que publicó la respuesta.
     *
     * @return El rol del usuario.
     */
    public String getRolUsuario() {
        return rolUsuario;
    }

    /**
     * Establece el rol del usuario que publicó la respuesta.
     *
     * @param rolUsuario El rol del usuario que se va a asignar a la respuesta.
     */
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * Obtiene el ID del usuario que publicó la respuesta.
     *
     * @return El ID del usuario.
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    /**
     * Establece el ID del usuario que publicó la respuesta.
     *
     * @param usuarioId El ID del usuario que se va a asignar a la respuesta.
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

}
