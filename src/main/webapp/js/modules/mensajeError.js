/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const mostrarMensajeError = (element, mensaje) => {
    element.parentElement.querySelector('.mensaje-error')?.remove();

    const mensajeError = document.createElement('span');
    mensajeError.classList.add('mensaje-error');
    mensajeError.textContent = mensaje;
    element.parentElement.appendChild(mensajeError);

    element.classList.add("error");
    element.classList.remove("correcto");
};

export const limpiarMensajeError = (event, element) => {
    if (element.value.trim() !== ""){
        element.parentElement.querySelector('.mensaje-error')?.remove();
        element.classList.add("correcto");
        element.classList.remove("error");
    }
};

export default mostrarMensajeError;
