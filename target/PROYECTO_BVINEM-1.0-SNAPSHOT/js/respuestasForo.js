/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

var quill = new Quill('#editor', {
    theme: 'snow'
});

var quillEdit = new Quill('#editorRespuesta', {
    theme: 'snow'
});

document.getElementById('respuForm').addEventListener('submit', function () {
    document.getElementById('respuesta').value = quill.root.innerHTML;
});

document.getElementById('editarRespuestaForm').addEventListener('submit', function () {
    document.getElementById('respuestaEditada').value = quillEdit.root.innerHTML;
});

function abrirModal(id) {
    let contenido = document.getElementById('contenidoRespuesta' + id).innerHTML;
    contenido = limpiarParrafosVacios(contenido); // Limpia los párrafos vacíos antes de insertar en Quill
    quillEdit.root.innerHTML = contenido;
    document.getElementById('respuestaIdEdit').value = id;
    document.getElementById('modalEditarRespuesta').style.display = 'block';
}

function limpiarParrafosVacios(contenido) {
    // Elimina las etiquetas <p> vacías
    contenido = contenido.replace(/<p>\s*<\/p>/g, '');
    return contenido.trim();
}

function cerrarModal() {
    document.getElementById('modalEditarRespuesta').style.display = 'none';
}

window.onclick = function (event) {
    if (event.target === document.getElementById('modalEditarRespuesta')) {
        cerrarModal();
    }
};