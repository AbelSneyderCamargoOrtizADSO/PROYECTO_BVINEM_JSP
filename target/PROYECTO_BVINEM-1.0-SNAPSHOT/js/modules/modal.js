/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// Abrir y cerrar modal
export const abrirModal = (modal) => {
    modal.classList.remove("display--none");
    modal.classList.add("modal");
};

export const cerrarModal = (modal) => {
    modal.classList.add("display--none");
    modal.classList.remove("modal");
};