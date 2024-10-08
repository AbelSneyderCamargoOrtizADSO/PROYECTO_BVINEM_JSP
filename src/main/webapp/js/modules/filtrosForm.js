/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const clearSelects = (event, selects) => {
    selects.forEach(select => {
        if (!select.value) { // Si esta vacio el valor del select devuelve falso (falsy) y se niega para que sea verdadero
            select.name = '';  // Eliminar el nombre del campo para que no sea enviado
        }
    });
};

export const activeSelects = (event, filters) => {
    filters.classList.toggle('active');

    if (filters.classList.contains('active')) {
        event.target.textContent = 'Ocultar Filtros';
    } else {
        event.target.textContent = 'Mostrar Filtros';
    }
};

export default clearSelects;


