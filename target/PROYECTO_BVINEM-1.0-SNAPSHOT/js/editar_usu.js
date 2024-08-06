/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import { validarFormularioCampos } from "./validaciones.js";

let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar Usuario";

// VARIABLES
let tipoUser = document.getElementById("tipoUsuario");
let btnEnviar = document.getElementById("btnEnviar");
const checkbox = document.getElementById('aceptarTerminos');
const submitButton = document.getElementById('btnEnviar');
const rol = document.getElementById("rol");
const selectRol = document.querySelectorAll(".select__rol");

selectRol.forEach(option => {
    if (option.value === rol.value) option.selected = true;
});

document.addEventListener('DOMContentLoaded', function () {

    checkbox.addEventListener('change', function () {
        submitButton.disabled = !checkbox.checked;
    });

    if (tipoUser.value === "estudiante") {
        submitButton.name = "editEstudiante";
    } else if (tipoUser.value === "docente") {
        submitButton.name = "editDocente";
    } else if (tipoUser.value === "administrador") {
        submitButton.name = "editAdmin";
    }
});

validarFormularioCampos();
