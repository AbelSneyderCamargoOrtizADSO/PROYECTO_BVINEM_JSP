/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import {abrirModal, cerrarModal} from './modules/modal.js';
import is_valid from './modules/valid.js';
import confirmar from './modules/confirmDelete.js';

// Variables
const editarForo = document.querySelector("#editarForoBtn");
const editarRespuBtn = document.querySelectorAll(".editarRespuBtn");
const modalEditarRespuesta = document.getElementById("modalEditarRespuesta");
const modalEditarForo = document.getElementById('modalEditarForo');
const cerrar = document.querySelectorAll(".modal__close");

const respuForm = document.getElementById('respuForm');
const respuesta = document.getElementById('respuesta');

const editarRespuestaForm = document.getElementById('editarRespuestaForm');
const respuestaEditada = document.getElementById('respuestaEditada');

const editarForoForm = document.getElementById('editarForoForm');
const foroEditado = document.getElementById('foroEditado');


// Asignar los textos enriquecidos a los textarea
var quill = new Quill('#editor', {
    theme: 'snow'
});

var quillEdit = new Quill('#editorRespuesta', {
    theme: 'snow'
});

var quillEditForo = new Quill('#editorForo', {
    theme: 'snow'
});

respuForm.addEventListener('submit', function () {
    respuesta.value = quill.root.innerHTML;
    is_valid(event, "#respuForm [required]");
});

editarRespuestaForm.addEventListener('submit', function () {
    respuestaEditada.value = quillEdit.root.innerHTML;
    is_valid(event, "#editarRespuestaForm [required]");
});

editarForoForm.addEventListener('submit', function () {
    foroEditado.value = quillEditForo.root.innerHTML;
    is_valid(event, "#editarForoForm [required]");
});


// Editar Foro
editarForo.addEventListener("click", () => abrirModalEditarForo());

function abrirModalEditarForo() {
    let contenido = document.querySelector('.present__text').innerHTML;
    contenido = limpiarParrafosVacios(contenido); // Limpia los párrafos vacíos antes de insertar en Quill
    quillEditForo.root.innerHTML = contenido;
    abrirModal(modalEditarForo);
}

// Editar Respuesta
function modalEditarRespu(id) {
    let contenido = document.getElementById('contenidoRespuesta' + id).innerHTML;
    contenido = limpiarParrafosVacios(contenido); // Limpia los párrafos vacíos antes de insertar en Quill
    quillEdit.root.innerHTML = contenido;
    document.getElementById('respuestaIdEdit').value = id;
    abrirModal(modalEditarRespuesta);
}

editarRespuBtn.forEach(btn => {
    let idRespuesta = btn.getAttribute('data-id');
    btn.addEventListener("click", () => modalEditarRespu(idRespuesta));
});

// Cerrar modal
cerrar.forEach(btn => {
    btn.addEventListener("click", () => cerrarModal(btn.closest(".modal")));
});

window.onclick = function (event) {
    if (event.target === modalEditarRespuesta) {
        cerrarModal(modalEditarRespuesta);
    }

    if (event.target === modalEditarForo) {
        cerrarModal(modalEditarForo);
    }
};

// Limpiar parrafos
function limpiarParrafosVacios(contenido) {
    // Elimina las etiquetas <p> vacías
    contenido = contenido.replace(/<p>\s*<\/p>/g, '');
    return contenido.trim();
}

// Confirmar eliminacion
//FORO
document.querySelectorAll('.eliminarForo').forEach(button => {
    button.addEventListener('click', (event) => {
        confirmar(event, "¿Estás seguro de eliminar este Foro?", "¡Esta acción no se puede deshacer!", "eliminarForo", "true");
    });
});

// RESPUESTAS
document.querySelectorAll('.eliminarRespu').forEach(button => {
    button.addEventListener('click', (event) => {
        confirmar(event, "¿Estás seguro de eliminar esta respuesta?", "¡Esta acción no se puede deshacer!", "eliminarRespu", "true");
    });
});
