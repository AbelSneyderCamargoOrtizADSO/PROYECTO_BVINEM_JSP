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
import validateImage, { aprobImage } from './modules/imageValid.js';
import validatePDF from './modules/pdfValid.js';

let titulo = document.querySelector(".header__title");
const title = document.querySelector("#titulo");
const autor = document.querySelector("#autor");
const descrip = document.querySelector("#descrip");
const year = document.querySelector("#year");
const asignatura = document.querySelector("#asignatura");
const idioma = document.querySelector("#idioma");
const tipo = document.querySelector("#tipo");
const miniatura = document.querySelector("#inputImagen");
const pdf = document.querySelector("#inputPdf");

const form = document.querySelector(".container form");
const letras = document.querySelectorAll("form .solo-letras");
const numeros = document.querySelectorAll("form .solo-numeros");

titulo.textContent = "Subir documento";

form.addEventListener("submit", (event) => {
    is_valid(event, "form [required]");
});

letras.forEach(campo => {
    campo.addEventListener("keypress", soloLetras);
});

numeros.forEach(campo => {
    campo.addEventListener("keypress", soloNumeros);
});

// Evento blur
title.addEventListener("blur", (event) => {
  vacio(event, title);
});

autor.addEventListener("blur", (event) => {
  vacio(event, autor);
});

descrip.addEventListener("blur", (event) => {
  vacio(event, descrip);
});

year.addEventListener("blur", (event) => {
  vacio(event, year);
});


// Limpiar mensajes
title.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, title);
});

autor.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, autor);
});

descrip.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, descrip);
});

year.addEventListener("keypress", (event) => {
  limpiarMensajeError(event, year);
});

limitedInputs("input[maxlength], textarea[maxlength]");

// Validar imagen
validateImage(miniatura);

// Validar PDF
validatePDF(pdf);

// Mostrar imagen
const imagePreview = document.getElementById('previsualizacionMiniatura');

miniatura.addEventListener('change', () => {
    const file = miniatura.files[0]; // Obtiene el primer archivo seleccionado
    if (file && aprobImage) {
        imagePreview.src = URL.createObjectURL(file);
    } else {
        imagePreview.removeAttribute("src");
    }
});