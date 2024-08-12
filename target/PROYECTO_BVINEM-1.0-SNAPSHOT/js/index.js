/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import vacio from './modules/vacios.js';
import is_valid from './modules/valid.js';
import soloLetras from './modules/letras.js';
import soloNumeros from './modules/numeros.js';
import { limpiarMensajeError } from './modules/mensajeError.js';
import limitedInputs from './modules/length.js';

let cestu = document.getElementById("container__estu");
let cdoc = document.getElementById("container__doc");
let cadmin = document.getElementById("container__admin");
let btnadmin = document.getElementById("btn__admin");
let btndoc = document.getElementById("btn__doc");
let btnestu = document.getElementById("btn__estu");

const formEstu = document.querySelector("#container__estu form");
const formDoc = document.querySelector("#container__doc form");
const formAdmin = document.querySelector("#container__admin form");

const dnis = document.querySelectorAll(".dni");
const passwords = document.querySelectorAll(".pass");
const letras = document.querySelectorAll("form .solo-letras");
const numeros = document.querySelectorAll("form .solo-numeros");

formEstu.addEventListener("submit", (event) => {
    is_valid(event, "#container__estu form [required]");
});

formDoc.addEventListener("submit", (event) => {
    is_valid(event, "#container__doc form [required]");
});

formAdmin.addEventListener("submit", (event) => {
    is_valid(event, "#container__admin form [required]");
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

numeros.forEach(campo => {
    campo.addEventListener("keypress", soloNumeros);
});

dnis.forEach(dni => {
    dni.addEventListener("blur", (event) => {
        vacio(event, dni);
    });

    dni.addEventListener("keypress", (event) => {
        limpiarMensajeError(event, dni);
    });
});

passwords.forEach(pass => {
    pass.addEventListener("blur", (event) => {
        vacio(event, pass);
    });
    
    pass.addEventListener("keypress", (event) => {
        limpiarMensajeError(event, pass);
    });
});

limitedInputs("input[maxlength], textarea[maxlength]");


// Escuchador de evento para el btn de administrador
btnadmin.addEventListener("click", function () {
    cestu.classList.add("disable");
    cdoc.classList.add("disable");
    cadmin.classList.remove("disable");
    btnadmin.classList.add("select");
    btndoc.classList.remove("select");
    btnestu.classList.remove("select");
});

// Escuchador de evento para el btn de docente
btndoc.addEventListener("click", function () {
    cestu.classList.add("disable");
    cdoc.classList.remove("disable");
    cadmin.classList.add("disable");
    btnadmin.classList.remove("select");
    btndoc.classList.add("select");
    btnestu.classList.remove("select");
});

// Escuchador de evento para el btn de estudiante
btnestu.addEventListener("click", function () {
    cestu.classList.remove("disable");
    cdoc.classList.add("disable");
    cadmin.classList.add("disable");
    btnadmin.classList.remove("select");
    btndoc.classList.remove("select");
    btnestu.classList.add("select");
});
