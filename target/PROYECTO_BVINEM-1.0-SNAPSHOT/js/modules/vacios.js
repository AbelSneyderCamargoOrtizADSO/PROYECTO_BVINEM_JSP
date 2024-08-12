/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import mostrarMensajeError from './mensajeError.js';

const vacio = (event, element) => {
    if (element.value.trim() === "" || element.value.trim() === "<p><br></p>") {
        mostrarMensajeError(element, "El campo es obligatorio");
    }
};

export default vacio;
