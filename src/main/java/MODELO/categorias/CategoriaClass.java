/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

/**
 * Clase que representa una categoría genérica. Esta clase puede ser extendida
 * por otras clases que necesiten atributos comunes de una categoría.
 *
 * @see IdiomaClass
 * @see TipoClass
 */
public class CategoriaClass {

    private int id;
    private String nombre;
    private boolean estado;

    /**
     * Constructor por defecto. Permite crear una instancia de
     * {@link CategoriaClass} sin inicializar sus propiedades.
     */
    public CategoriaClass() {
    }

    /**
     * Constructor con parámetros para inicializar una categoría con datos
     * específicos.
     *
     * @param id El ID de la categoría.
     * @param nombre El nombre de la categoría.
     * @param estado El estado de la categoría (activo o inactivo).
     */
    public CategoriaClass(int id, String nombre, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    /**
     * Obtiene el ID de la categoría.
     *
     * @return El ID de la categoría.
     */
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el ID de la categoría.
     *
     * @param id El nuevo ID de la categoría.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre.
     *
     * @param nombre El nombre que se va a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Verifica si el estado es verdadero o falso.
     *
     * @return El estado actual (true o false).
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Establece el estado.
     *
     * @param estado El estado que se va a asignar (true o false).
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
