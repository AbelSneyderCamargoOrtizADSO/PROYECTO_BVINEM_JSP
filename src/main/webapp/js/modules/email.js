/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import mostrarMensajeError, { limpiarMensajeError } from './mensajeError.js';

const email = (event, element) => {
    let regex = /^[\w-._]+@[\w-._]+(\.[a-zA-Z]{2,4}){1,2}$/;

    if (regex.test(element.value)) {
        limpiarMensajeError(event, element);
    } else {
        event.preventDefault();
        mostrarMensajeError(element, "Correo electrónico no válido");
    }
};

export default email;


