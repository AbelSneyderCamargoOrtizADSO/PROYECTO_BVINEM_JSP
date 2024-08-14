/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import clearSelects, { activeSelects } from './modules/filtrosForm.js';

const filterForm = document.getElementById('filter__form');
const filterToggle = document.getElementById('filterToggle');

// EVITAR ENVIO DE FORMULARIO CON FILTROS VACIOS
// Responsive de filtros
filterToggle.addEventListener('click', (event) => {
    activeSelects(event, filterForm);
});

filterForm.addEventListener('submit', (event) => {
    let selects = filterForm.querySelectorAll('select');
    clearSelects(event, selects);
});
