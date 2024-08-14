/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
import confirmar from './modules/confirmDelete.js';
import {disableSearch} from './modules/disableBtn.js';


// Poner título al header
let titulo = document.querySelector(".header__title");
titulo.textContent = "Gestión de Usuarios";

// Variables
const inputBuscar = document.getElementById("docUsuario");
const btnBuscar = document.getElementById("filtrar");
const agregarDocenteBtn = document.getElementById('agregarDocenteBtn');
const agregarAdminBtn = document.getElementById("agregarAdminBtn");

// Formulario de buscar usuario por ID
inputBuscar.addEventListener("input", () => {
    disableSearch(inputBuscar, btnBuscar);
});

// Confirmar eliminacion
document.querySelectorAll('.eliminarUsuario').forEach(button => {
    button.addEventListener('click', (event) => {
        confirmar(event, "¿Estás seguro de inhabilitar este Usuario?", "Podrás habilitarlo nuevamente", "actionDel", "inhabilitarUsu");
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const tipoUsuario = urlParams.get('tipoUsuario');

    if (tipoUsuario === 'estudiante') {
        agregarDocenteBtn.style.display = 'none';
        if (agregarAdminBtn) agregarAdminBtn.style.display = 'none';
    } else if (tipoUsuario === 'administrador') {
        agregarDocenteBtn.style.display = 'none';
        if (agregarAdminBtn) agregarAdminBtn.style.display = 'inline-block';
    } else {
        agregarDocenteBtn.style.display = 'inline-block';
        if (agregarAdminBtn) agregarAdminBtn.style.display = 'none';
    }
});

/*
// Limpiarlo
document.getElementById('buscarUsuarioForm').addEventListener('submit', function (event) {
    // Obtener todos los inputs del formulario
    let inputs = this.querySelectorAll('input');

    // Iterar sobre los inputs y eliminar aquellos que están vacíos
    inputs.forEach(input => {
        if (!input.value) {
            input.name = '';  // Eliminar el nombre del campo para que no sea enviado
        }
    });
});
 */
