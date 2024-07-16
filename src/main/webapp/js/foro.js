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

var quillEditForo = new Quill('#editorForo', {
    theme: 'snow'
});

document.getElementById('respuForm').addEventListener('submit', function () {
    document.getElementById('respuesta').value = quill.root.innerHTML;
});

document.getElementById('editarRespuestaForm').addEventListener('submit', function () {
    document.getElementById('respuestaEditada').value = quillEdit.root.innerHTML;
});

document.getElementById('editarForoForm').addEventListener('submit', function () {
    document.getElementById('foroEditado').value = quillEditForo.root.innerHTML;
});

function abrirModal(id) {
    let contenido = document.getElementById('contenidoRespuesta' + id).innerHTML;
    contenido = limpiarParrafosVacios(contenido); // Limpia los párrafos vacíos antes de insertar en Quill
    quillEdit.root.innerHTML = contenido;
    document.getElementById('respuestaIdEdit').value = id;
    document.getElementById('modalEditarRespuesta').style.display = 'block';
}

function abrirModalEditarForo() {
    let contenido = document.querySelector('.present__text').innerHTML;
    contenido = limpiarParrafosVacios(contenido); // Limpia los párrafos vacíos antes de insertar en Quill
    quillEditForo.root.innerHTML = contenido;
    document.getElementById('modalEditarForo').style.display = 'block';
}

function limpiarParrafosVacios(contenido) {
    // Elimina las etiquetas <p> vacías
    contenido = contenido.replace(/<p>\s*<\/p>/g, '');
    return contenido.trim();
}

function cerrarModal() {
    document.getElementById('modalEditarRespuesta').style.display = 'none';
}

function cerrarModalEditarForo() {
    document.getElementById('modalEditarForo').style.display = 'none';
}

window.onclick = function (event) {
    if (event.target === document.getElementById('modalEditarRespuesta')) {
        cerrarModal();
    }

    if (event.target === document.getElementById('modalEditarForo')) {
        cerrarModalEditarForo();
    }
}

//FORO
document.querySelectorAll('.eliminarForo').forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form'); // Encuentra el formulario más cercano

        Swal.fire({
            title: '¿Estás seguro de eliminar este Foro?',
            text: "¡Esta acción no se puede deshacer!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // Se creal el input con name = "eliminarRespu" en true para que sea identificado en la condicional del servlet 
                const inputEliminar = document.createElement('input');
                inputEliminar.type = 'hidden';
                inputEliminar.name = 'eliminarForo';
                inputEliminar.value = 'true';
                form.appendChild(inputEliminar);

                form.submit(); // Envía el formulario si el usuario confirma
            }
        });
    });
});

// RESPUESTAS
document.querySelectorAll('.eliminarRespu').forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form'); // Encuentra el formulario más cercano

        Swal.fire({
            title: '¿Estás seguro de eliminar esta respuesta?',
            text: "¡Esta acción no se puede deshacer!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // Se creal el input con name = "eliminarRespu" en true para que sea identificado en la condicional del servlet 
                const inputEliminar = document.createElement('input');
                inputEliminar.type = 'hidden';
                inputEliminar.name = 'eliminarRespu';
                inputEliminar.value = 'true';
                form.appendChild(inputEliminar);

                form.submit(); // Envía el formulario si el usuario confirma
            }
        });
    });
});