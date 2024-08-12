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
titulo.textContent = "Registrar Usuario";

// VARIABLES
let tipoUser = document.getElementById("tipoUsuario");
let btnEnviar = document.getElementById("btnEnviar");
const checkbox = document.querySelector("#aceptarTerminos");

const dni = document.querySelector("#documento");
const nombres = document.querySelector("#nombres");
const apellidos = document.querySelector("#apellidos");
const correo = document.querySelector("#correo");
const password = document.querySelector("#contrasena");
const confirmPass = document.querySelector("#confirContra");

const form = document.querySelector(".container form");
const letras = document.querySelectorAll("form .solo-letras");
const numeros = document.querySelectorAll("form .solo-numeros");

form.addEventListener("submit", (event) => {
    is_valid(event, "form [required]");
    email(event, correo);
    confirmarPass(event, password, confirmPass);
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

numeros.forEach(campo => {
    campo.addEventListener("keypress", soloNumeros);
});

// Verificar vacios
dni.addEventListener("blur", (event) => {
  vacio(event, dni);
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

password.addEventListener("blur", (event) => {
  confirmarPass(event, password, confirmPass);
});

confirmPass.addEventListener("blur", (event) => {
  confirmarPass(event, password, confirmPass);
});

// Limpiar mensajes
dni.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, dni);
});

nombres.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, nombres);
});

apellidos.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, apellidos);
});

limitedInputs("input[maxlength]");

disableBtn(checkbox, btnEnviar);

if (tipoUser.value === "docente") {
    btnEnviar.name = "regDocente";
} else if (tipoUser.value === "administrador") {
    btnEnviar.name = "regAdmin";
}

