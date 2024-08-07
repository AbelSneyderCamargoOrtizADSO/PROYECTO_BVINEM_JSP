/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import { validarFormularioCampos } from "./validaciones.js";

validarFormularioCampos();

let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar las categorias";

// VARIABLES
let btnsEditar = document.querySelectorAll(".editar");
let formEditar = document.getElementById("formEditar");
let formAgregar = document.getElementById("formAgregar");
let inputId = document.getElementById("editId");
let inputNombre = document.getElementById("editNombre");
let btnAgregar = document.getElementById("btnAgregar");


btnAgregar.addEventListener("click", () => {
    formAgregar.classList.remove("display--none");
    formAgregar.classList.add("modal");
});

btnsEditar.forEach(btn => {
    btn.addEventListener("click", () => {
        inputId.value = btn.dataset.id;
        inputNombre.value = btn.dataset.nombre;

        formEditar.classList.remove("display--none");
        formEditar.classList.add("modal");
    });
});

document.querySelectorAll(".modal__close").forEach(button => {
    button.addEventListener("click", () => {
        button.closest(".modal").classList.add("display--none");
    });
});

document.querySelectorAll(".Inhabilitar").forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form');
        Swal.fire({
            title: '¿Estás segur@?',
            text: "Podrás habilitarlo nuevamente",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, inhabilitar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const inputEliminar = document.createElement('input');
                inputEliminar.type = 'hidden';
                inputEliminar.name = 'action';
                inputEliminar.value = 'inhabilitar';
                form.appendChild(inputEliminar);
                form.submit();
            }
        });
    });
});

