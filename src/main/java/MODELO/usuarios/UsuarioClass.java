/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene los atributos y métodos para gestionar la información de un usuario.
 * 
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
    
    /**
     * Constructor por defecto.
     * Permite crear una instancia de {@link UsuarioClass} sin inicializar sus propiedades.
     */
    public UsuarioClass() {
    }
    
    /**
     * Constructor con parámetros para inicializar un usuario con datos específicos.
     * 
     * @param docUsu El documento de identificación del usuario.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param correo El correo electrónico del usuario.
     * @param fechaRegistro La fecha de registro del usuario.
     * @param estadoId El estado del usuario (activo/inactivo).
     * @param rol El rol del usuario en el sistema.
     */
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
    
    /**
     * Obtiene el documento de identificación del usuario.
     * 
     * @return El documento de identificación del usuario.
     */
    // Getters y Setters
    public int getDocUsu() {
        return docUsu;
    }
    
    /**
     * Establece el documento de identificación del usuario.
     * 
     * @param docUsu El nuevo documento de identificación del usuario.
     */
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
