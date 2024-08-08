/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

/**
 * Clase que representa una asignatura.
 * Extiende de {@link CategoriaClass} e incluye los atributos y métodos específicos de una asignatura.
 * 
 * @see CategoriaClass
 */

public class AsignaturaClass extends CategoriaClass{
    /**
     * Constructor por defecto.
     * Permite crear una instancia de {@link AsignaturaClass} sin inicializar sus propiedades.
     * Utiliza el constructor por defecto de la clase padre {@link CategoriaClass}.
     */
    public AsignaturaClass() {
        super();
    }
    
    /**
     * Constructor con parámetros para inicializar una asignatura con datos específicos.
     * 
     * @param id El ID de la asignatura.
     * @param nombre El nombre de la asignatura.
     * @param estado El estado de la asignatura (activo o inactivo).
     * 
     * Este constructor llama al constructor de la clase padre y le asigna 
     * las variables de id, nombre y estado, para que se asignen a la clase {@link CategoriaClass}.
     */
    public AsignaturaClass(int id, String nombre, boolean estado) { // Lo que hace es llamar al cosntructor de la clase padre y asignarle a este constructor las variables de id y nombre, para que se le asignen a la clase categoria (como si fuese por set)
        super(id, nombre, estado); // Llama al constructor de la clase padre y asigna los valores
    }
}
