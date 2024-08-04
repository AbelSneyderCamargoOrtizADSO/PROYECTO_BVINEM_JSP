/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import {validarFormularioCampos} from "./validaciones.js"

validarFormularioCampos();

const btnEditar = document.querySelectorAll('.editDoc');
const editId = document.getElementById('editId');
const editTitulo = document.getElementById('editTitulo');
const editAutor = document.getElementById('editAutor');
const editDescripcion = document.getElementById('editDescripcion');
const editYear = document.getElementById('editYear');
const closeModal = document.getElementById('editModal');


btnEditar.forEach(btn => {
    btn.addEventListener("click", function () {
        const book = this.closest('.books__book');

        editId.value = book.dataset.id;
        editTitulo.value = book.dataset.titulo;
        editAutor.value = book.dataset.autor;
        editDescripcion.value = book.dataset.descripcion;
        editYear.value = book.dataset.year;

        closeModal.style.display = 'block';
    });
});

// Añadir un evento de clic para cerrar el modal
document.getElementById('closeModal').addEventListener('click', function () {
    document.getElementById('editModal').style.display = 'none';
});

// EVITAR ENVIO DE FORMULARIO CON FILTROS VACIOS
document.getElementById('filter__form').addEventListener('submit', function (event) {
    // Obtener todos los select del formulario
    let selects = this.querySelectorAll('select'); // Aquí, this hace referencia al elemento al que se adjunto el evento addEventListener (es decir el formulario) 

    // Iterar sobre los select y eliminar aquellos que están vacíos
    selects.forEach(select => {
        if (!select.value) {
            select.name = '';  // Eliminar el nombre del campo para que no sea enviado
        }
    });
});

document.querySelectorAll('.eliminarDoc').forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form');
        Swal.fire({
            title: '¿Estás seguro de eliminar este libro?',
            text: "Esta opción no es reversible",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const inputEliminar = document.createElement('input');
                inputEliminar.type = 'hidden';
                inputEliminar.name = 'eliminarDocumento';
                inputEliminar.value = 'true';
                form.appendChild(inputEliminar);
                form.submit();
            }
        });
    });
});

