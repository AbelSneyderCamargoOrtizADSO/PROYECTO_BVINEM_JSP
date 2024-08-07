/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.categorias;

/**
 *
 * @author Abelito
 */
public class TipoClass extends CategoriaClass{
    public TipoClass() {
        super();
    }
    
    public TipoClass(int id, String nombre, boolean estado) { // Lo que hace es llamar al cosntructor de la clase padre y asignarle a este constructor las variables de id y nombre, para que se le asignen a la clase categoria (como si fuese por set)
        super(id, nombre, estado); // Llama al constructor de la clase padre y asigna los valores
    }
}
