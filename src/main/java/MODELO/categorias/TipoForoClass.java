/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

/**
 * Clase que representa un tipo de foro.
 * Extiende de {@link CategoriaClass} e incluye los atributos y métodos específicos de un tipo de foro.
 * 
 * @see CategoriaClass
 */
public class TipoForoClass extends CategoriaClass {
    /**
     * Constructor por defecto.
     * Permite crear una instancia de {@link TipoForoClass} sin inicializar sus propiedades.
     * Utiliza el constructor por defecto de la clase padre {@link CategoriaClass}.
     */
    public TipoForoClass() {
        super();
    }
    
    /**
     * Constructor con parámetros para inicializar un tipo de foro con datos específicos.
     * 
     * @param id El ID del tipo de foro.
     * @param nombre El nombre del tipo de foro.
     * @param estado El estado del tipo de foro (activo o inactivo).
     * 
     * Lo que hace es llamar al constructor de la clase padre y asignar a este constructor
     * las variables de id, nombre y estado, para que se asignen a la clase {@link CategoriaClass}.
     */
    public TipoForoClass(int id, String nombre, boolean estado) { 
        super(id, nombre, estado); // Llama al constructor de la clase padre y asigna los valores
    }
}
