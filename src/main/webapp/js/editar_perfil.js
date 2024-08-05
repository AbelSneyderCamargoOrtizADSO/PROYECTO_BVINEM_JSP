/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import { validarFormularioCampos, habilitarBotonAlEntrada } from "./validaciones.js";

let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar mis datos";

// VARIABLES
let tipoUser = document.getElementById("tipoUsuario");
let btnEnviar = document.getElementById("btnEnviar");
let body = document.getElementById("body");
let form = document.getElementById("form");
const inputs = form.querySelectorAll(".form__input");
const btn = document.querySelector(".form__btn");


document.addEventListener('DOMContentLoaded', function () {
    btn.disabled = true;
    habilitarBotonAlEntrada(inputs, btn);

    if (tipoUser.value === "1") {
        tipoUser.value = "estudiante";
        btnEnviar.name = "editEstudiante";
    } else if (tipoUser.value === "2") {
        tipoUser.value = "docente";
        btnEnviar.name = "editDocente";
    } else if (tipoUser.value === "3" || tipoUser.value === "4") {
        tipoUser.value = "administrador";
        btnEnviar.name = "editAdmin";
        body.style.background = "var(--blue3)";
    }
});

validarFormularioCampos();