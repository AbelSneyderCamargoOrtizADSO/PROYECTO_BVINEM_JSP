/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import vacio from './modules/vacios.js';
import is_valid from './modules/valid.js';
import soloLetras from './modules/letras.js';
import { limpiarMensajeError } from './modules/mensajeError.js';
import limitedInputs from './modules/length.js';
import {abrirModal, cerrarModal} from './modules/modal.js';
import confirmar from './modules/confirmDelete.js';

let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar las categorias";

// VARIABLES
let btnsEditar = document.querySelectorAll(".editar");
let formEditar = document.getElementById("formEditar");
let formAgregar = document.getElementById("formAgregar");
let inputId = document.getElementById("editId");
let inputNombre = document.getElementById("editNombre");
let btnAgregar = document.getElementById("btnAgregar");
const letras = document.querySelectorAll("form .solo-letras");
const inputs = document.querySelectorAll(".modal__input");

// Modal
// Abrir modal agregar
btnAgregar.addEventListener("click", () => {
    abrirModal(formAgregar);
});

// Abrir modal editar
btnsEditar.forEach(btn => {
    btn.addEventListener("click", () => {
        inputId.value = btn.dataset.id;
        inputNombre.value = btn.dataset.nombre;
        abrirModal(formEditar);
    });
});

// Cerrar modales
document.querySelectorAll(".modal__close").forEach(button => {
    button.addEventListener("click", () => {
        cerrarModal(button.closest(".modal"));
    });
});

// Validaciones
formEditar.addEventListener("submit", (event) => {
    is_valid(event, "#formEditar [required]");
});

formAgregar.addEventListener("submit", (event) => {
    is_valid(event, "#formAgregar [required]");
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

inputs.forEach(campo => {
    campo.addEventListener("blur", (event) => {
        vacio(event, campo);
    });

    campo.addEventListener("keypress", (event) => {
        limpiarMensajeError(event, campo);
    });
});

limitedInputs("input[maxlength]");

// Confirmar eliminacion
document.querySelectorAll('.Inhabilitar').forEach(button => {
    button.addEventListener('click', (event) => {
        confirmar(event, "¿Estás segur@?", "Podrás habilitarlo nuevamente", "action", "inhabilitar");
    });
});

