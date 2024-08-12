/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
import vacio from './modules/vacios.js';
import is_valid from './modules/valid.js';
import { limpiarMensajeError } from './modules/mensajeError.js';
import limitedInputs from './modules/length.js';

let titulo = document.querySelector(".header__title");
titulo.textContent = "Crear Nuevo Foro";

// VARIABLES
const title = document.querySelector("#titulo");
const asignatura = document.querySelector("#asignatura");
const idioma = document.querySelector("#idioma");
const tipof = document.querySelector("#tipof");
const form = document.getElementById("foroForm");
const descripcion = document.getElementById('descripcion');

var quill = new Quill('#editor', {
    theme: 'snow'
});

document.getElementById('foroForm').addEventListener('submit', function () {
    descripcion.value = quill.root.innerHTML;
});

form.addEventListener("submit", (event) => {
    is_valid(event, "form [required]");
});

title.addEventListener("blur", (event) => {
  vacio(event, title);
});

asignatura.addEventListener("blur", (event) => {
  vacio(event, asignatura);
});

idioma.addEventListener("blur", (event) => {
  vacio(event, idioma);
});

tipof.addEventListener("blur", (event) => {
  vacio(event, tipof);
});

// Limpiar mensajes
title.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, title);
});

asignatura.addEventListener("change", (event) => {
  limpiarMensajeError(event, asignatura);
});

idioma.addEventListener("change", (event) => {
  limpiarMensajeError(event, idioma);
});

tipof.addEventListener("change", (event) => {
  limpiarMensajeError(event, tipof);
});


