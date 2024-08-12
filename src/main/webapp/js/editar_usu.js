/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import vacio from './modules/vacios.js';
import email from './modules/email.js';
import is_valid from './modules/valid.js';
import soloLetras from './modules/letras.js';
import soloNumeros from './modules/numeros.js';
import { limpiarMensajeError } from './modules/mensajeError.js';
import confirmarPass from './modules/confirmPass.js';
import limitedInputs from './modules/length.js';
import disableBtn from './modules/disableBtn.js';

let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar Usuario";

// VARIABLES
let tipoUser = document.getElementById("tipoUsuario");
let btnEnviar = document.getElementById("btnEnviar");
const checkbox = document.getElementById('aceptarTerminos');
const submitButton = document.getElementById('btnEnviar');
const rol = document.getElementById("rol");
const selectRol = document.querySelectorAll(".select__rol");

const documento = document.querySelector("#documento");
const nombres = document.querySelector("#nombres");
const apellidos = document.querySelector("#apellidos");
const correo = document.querySelector("#correo");
const contrasena = document.querySelector("#contrasena");
const confirContra = document.querySelector("#confirContra");
const letras = document.querySelectorAll("form .solo-letras");
const numeros = document.querySelectorAll("form .solo-numeros");

form.addEventListener("submit", (event) => {
    is_valid(event, "form [required]");
    email(event, correo);
    confirmarPass(event, contrasena, confirContra);
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

numeros.forEach(campo => {
    campo.addEventListener("keypress", soloNumeros);
});

documento.addEventListener("blur", (event) => {
  vacio(event, documento);
});

nombres.addEventListener("blur", (event) => {
  vacio(event, nombres);
});

apellidos.addEventListener("blur", (event) => {
  vacio(event, apellidos);
});

correo.addEventListener("blur", (event) => {
  email(event, correo);
});

contrasena.addEventListener("blur", (event) => {
  confirmarPass(event, contrasena, confirContra);
});

confirContra.addEventListener("blur", (event) => {
  confirmarPass(event, contrasena, confirContra);
});

// Limpiar mensajes
documento.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, documento);
});

nombres.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, nombres);
});

apellidos.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, apellidos);
});

limitedInputs("input[maxlength]");

// Seleccionar el rol correspondiente
selectRol.forEach(option => {
    if (option.value === rol.value) option.selected = true;
});

document.addEventListener('DOMContentLoaded', function () {
    disableBtn(checkbox, submitButton);
    
    if (tipoUser.value === "estudiante") {
        submitButton.name = "editEstudiante";
    } else if (tipoUser.value === "docente") {
        submitButton.name = "editDocente";
    } else if (tipoUser.value === "administrador") {
        submitButton.name = "editAdmin";
    }
});
