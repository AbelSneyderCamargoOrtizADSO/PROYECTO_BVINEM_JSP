/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
import { validarFormularioCampos, habilitarBotonAlEntrada } from "./validaciones.js";

let titulo = document.querySelector(".header__title");
titulo.textContent = "Registrar Usuario";

// VARIABLES
let tipoUser = document.getElementById("tipoUsuario");
let btnEnviar = document.getElementById("btnEnviar");
const inputs = form.querySelectorAll(".form__input");
const btn = document.querySelector(".form__btn");

if (tipoUser.value === "docente") {
    btnEnviar.name = "regDocente";
} else if (tipoUser.value === "administrador") {
    btnEnviar.name = "regAdmin";
}

habilitarBotonAlEntrada(inputs, btn);

validarFormularioCampos();
