/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import mostrarMensajeError, { limpiarMensajeError } from './mensajeError.js';

const confirmarPass = (event, pass, confirm) => {
    let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    
    // SI LOS CAMPOS ESTAN VACIOS Y NO TIENEN REQUIRED NO SE VALIDA (SIGNIFICA QUE SE ESTA EDITANDO USUARIO Y NO SE VA A CAMBIAR CONTRASEÑA)
    if (pass.value === '' && confirm.value === '') {
        limpiarMensajeError(event, pass);
        limpiarMensajeError(event, confirm);
        return;
    }
    
    if (!regex.test(pass.value)) {
        event.preventDefault();
        mostrarMensajeError(pass, "Mín. 8 caracteres, mayús., minús., y números.");
    } else {
        limpiarMensajeError(event, pass);
    }

    if (pass.value !== confirm.value) {
        event.preventDefault();
        mostrarMensajeError(confirm, "La contraseña no coincide");
    } else {
        limpiarMensajeError(event, confirm);
    }
};

export default confirmarPass;
