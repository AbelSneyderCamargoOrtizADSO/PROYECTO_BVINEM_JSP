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
    private String idioma;
    private String asignatura;
    private String tipo;

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

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
