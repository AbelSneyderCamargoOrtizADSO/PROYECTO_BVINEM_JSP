/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

/**
 *
 * @author Abelito
 */
public class UsuarioClass {

    private int docUsu;
    private String nombre;
    private String apellido;
    private String correo;
    private String fechaRegistro;
    private String pass;
    private int estadoId;
    private int rol;
    private String grado;

    public UsuarioClass() {
    }

    // Constructor
    public UsuarioClass(int docUsu, String nombre, String apellido, String correo, String fechaRegistro, int estadoId, int rol) {
        this.docUsu = docUsu;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.estadoId = estadoId;
        this.rol = rol;
    }

    // Getters y Setters
    public int getDocUsu() {
        return docUsu;
    }

    public void setDocUsu(int docUsu) {
        this.docUsu = docUsu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }
    
    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
    
    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    // Métodos get: Utilizados para obtener el valor de una variable privada.
    // Métodos set: Utilizados para asignar o modificar el valor de una variable privada.
}
