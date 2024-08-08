/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

/**
 * Clase que representa un tipo de documento o categoría específica.
 * Extiende de {@link CategoriaClass} e incluye los atributos y métodos específicos de un tipo.
 * 
 * @see CategoriaClass
 */
public class TipoClass extends CategoriaClass{
    /**
     * Constructor por defecto.
     * Permite crear una instancia de {@link TipoClass} sin inicializar sus propiedades.
     * Utiliza el constructor por defecto de la clase padre {@link CategoriaClass}.
     */
    public TipoClass() {
        super();
    }
    
    /**
     * Constructor con parámetros para inicializar un tipo con datos específicos.
     * 
     * @param id El ID del tipo.
     * @param nombre El nombre del tipo.
     * @param estado El estado del tipo (activo o inactivo).
     * 
     * Lo que hace es llamar al constructor de la clase padre y asignar a este constructor
     * las variables de id, nombre y estado, para que se asignen a la clase {@link CategoriaClass}.
     */
    public TipoClass(int id, String nombre, boolean estado) { // Lo que hace es llamar al cosntructor de la clase padre y asignarle a este constructor las variables de id y nombre, para que se le asignen a la clase categoria (como si fuese por set)
        super(id, nombre, estado); // Llama al constructor de la clase padre y asigna los valores
    }
}
