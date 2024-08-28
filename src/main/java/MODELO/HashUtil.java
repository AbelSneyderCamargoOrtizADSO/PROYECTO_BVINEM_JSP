/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria para realizar operaciones de hashing.
 * Proporciona métodos para hashear contraseñas utilizando SHA-256.
 * 
 */
public class HashUtil {
    /**
     * Método para hashear una contraseña utilizando SHA-256.
     * 
     * @param password La contraseña en texto plano que se desea hashear.
     * @return La contraseña hasheada en formato hexadecimal.
     * @throws RuntimeException Si ocurre un error al obtener la instancia de MessageDigest.
     * @see <a href="https://www.youtube.com/watch?v=MoDSZFIh34Y&ab_channel=CodeGuide">Code Guide</a>
     */
    public static String hashPassword(String password) {
        try {
            // Obtener una instancia del algoritmo de hashing SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Aplicar el algoritmo de hashing a la contraseña
            byte[] hashBytes = md.digest(password.getBytes());
            // Convertir el resultado del hashing a una cadena hexadecima
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            // Devolver la cadena hexadecimal resultante
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Lanzar una excepción en caso de que el algoritmo de hashing no esté disponible
            throw new RuntimeException(e);
        }
    }
    
    // Método basado en el tutorial de Code Guide
}
