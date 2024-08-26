/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

/**
 * Clase que representa un usuario en el sistema. Contiene los atributos y
 * métodos para gestionar la información de un usuario.
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
     * Constructor por defecto. Permite crear una instancia de
     * {@link UsuarioClass} sin inicializar sus propiedades.
     */
    public UsuarioClass() {
    }

    /**
     * Constructor con parámetros para inicializar un usuario con datos
     * específicos.
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

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre que se va a asignar al usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return El apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param apellido El apellido que se va a asignar al usuario.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el correo del usuario.
     *
     * @return El correo del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo del usuario.
     *
     * @param correo El correo que se va a asignar al usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la fecha de registro del usuario.
     *
     * @return La fecha de registro del usuario.
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del usuario.
     *
     * @param fechaRegistro La fecha de registro que se va a asignar al usuario.
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param pass La contraseña que se va a asignar al usuario.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Obtiene el estado del usuario.
     *
     * @return El ID del estado del usuario.
     */
    public int getEstadoId() {
        return estadoId;
    }

    /**
     * Establece el estado del usuario.
     *
     * @param estadoId El ID del estado que se va a asignar al usuario.
     */
    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public int getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El rol que se va a asignar al usuario.
     */
    public void setRol(int rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el grado del usuario.
     *
     * @return El grado del usuario.
     */
    public String getGrado() {
        return grado;
    }

    /**
     * Establece el grado del usuario.
     *
     * @param grado El grado que se va a asignar al usuario.
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }

    // Métodos get: Utilizados para obtener el valor de una variable privada.
    // Métodos set: Utilizados para asignar o modificar el valor de una variable privada.
}
