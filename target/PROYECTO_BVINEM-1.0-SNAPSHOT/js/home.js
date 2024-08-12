/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import vacio from './modules/vacios.js';
import is_valid from './modules/valid.js';
import soloLetras from './modules/letras.js';
import soloNumeros from './modules/numeros.js';
import { limpiarMensajeError } from './modules/mensajeError.js';
import limitedInputs from './modules/length.js';
import {abrirModal, cerrarModal} from './modules/modal.js';
import clearSelects from './modules/filtrosForm.js';
import confirmar from './modules/confirmDelete.js';

const btnEditar = document.querySelectorAll('.editDoc');
const editId = document.getElementById('editId');
const editTitulo = document.getElementById('editTitulo');
const editAutor = document.getElementById('editAutor');
const editDescripcion = document.getElementById('editDescripcion');
const editYear = document.getElementById('editYear');
const modal = document.getElementById('editModal');
const closeModal = document.getElementById('closeModal');
const filterForm = document.getElementById('filter__form');

const form = document.querySelector("#editForm");
const letras = document.querySelectorAll("form .solo-letras");
const numeros = document.querySelectorAll("form .solo-numeros");
const inputs = document.querySelectorAll(".form__input");

// Modal
btnEditar.forEach(btn => {
    btn.addEventListener("click", function () {
        const book = this.closest('.books__book');

        editId.value = book.dataset.id;
        editTitulo.value = book.dataset.titulo;
        editAutor.value = book.dataset.autor;
        editDescripcion.value = book.dataset.descripcion;
        editYear.value = book.dataset.year;

        abrirModal(modal);
    });
});

closeModal.addEventListener('click', function () {
    cerrarModal(modal);
});

// Validaciones
form.addEventListener("submit", (event) => {
    is_valid(event, "form [required]");
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

numeros.forEach(campo => {
    campo.addEventListener("keypress", soloNumeros);
});

inputs.forEach(campo => {
    campo.addEventListener("blur", (event) => {
        vacio(event, campo);
    });

    campo.addEventListener("keypress", (event) => {
        limpiarMensajeError(event, campo);
    });
});

// EVITAR ENVIO DE FORMULARIO CON FILTROS VACIOS
filterForm.addEventListener('submit', function (event) {
    let selects = this.querySelectorAll('select');  
    clearSelects(event, selects);
});

document.querySelectorAll('.eliminarDoc').forEach(button => {
    button.addEventListener('click', (event) => {
        confirmar(event, "¿Estás seguro de eliminar este libro?", "Esta opción no es reversible", "eliminarDocumento", "true");
    });
});

limitedInputs("input[maxlength]");

