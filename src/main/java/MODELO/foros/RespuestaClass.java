/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

/**
 *
 * @author Abelito
 */
public class RespuestaClass {

    private int id;
    private String contenido;
    private String fechaPublicacion;
    private String nombreUsuario;
    private String rolUsuario;
    private String gradoEstu;
    private int usuarioId;

    public RespuestaClass(int id, String contenido, String fechaPublicacion, String nombreUsuario, String rolUsuario, String gradoEstu, int usuarioId) {
        this.id = id;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;
        this.gradoEstu = gradoEstu;
        this.usuarioId = usuarioId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public String getGradoEstu() {
        return gradoEstu;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}
