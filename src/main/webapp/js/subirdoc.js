/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
import { aprobImage } from "./validaciones.js";

const inputImage = document.getElementById('inputImagen');
const imagePreview = document.getElementById('previsualizacionMiniatura');

inputImage.addEventListener('change', () => {
    const file = inputImage.files[0]; // Obtiene el primer archivo seleccionado
    if (file && aprobImage) {
        imagePreview.src = URL.createObjectURL(file);
    } else {
        imagePreview.removeAttribute("src");
    }
});