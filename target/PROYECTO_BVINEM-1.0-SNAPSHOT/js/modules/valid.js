/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
import mostrarMensajeError from './mensajeError.js';

const is_valid = (event, selector) => {
    const elementos = document.querySelectorAll(selector);
    elementos.forEach(campo => {
        if (campo.value.trim() === "" || campo.value.trim() === "<p><br></p>") {
            event.preventDefault();
            mostrarMensajeError(campo, "El campo es obligatorio");
        }
    });
};

export default is_valid;
