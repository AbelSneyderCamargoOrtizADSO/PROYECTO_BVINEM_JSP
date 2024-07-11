/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Abelito
 */

public class IdiomaClass {
    // Declara una variable privada de tipo int para almacenar el ID del idioma
    private int id;
    // Declara una variable privada de tipo String para almacenar el nombre del idioma
    private String nombre;

    // Constructor de la clase IdiomaClass que toma dos parámetros: id y nombre
    public IdiomaClass(int id, String nombre) {
        // Asigna el valor del parámetro id a la variable de instancia id
        this.id = id;
        // Asigna el valor del parámetro nombre a la variable de instancia nombre
        this.nombre = nombre;
    }

    // Método getter para obtener el valor de la variable id
    public int getId() {
        return id;
    }

    // Método getter para obtener el valor de la variable nombre
    public String getNombre() {
        return nombre;
    }
}

