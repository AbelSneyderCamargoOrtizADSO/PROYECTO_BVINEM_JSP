/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const agregarAdminBtn = document.getElementById("agregarAdminBtn");

// Poner título al header
let titulo = document.querySelector(".header__title");
titulo.textContent = "Gestión de Usuarios";
const inputBuscar = document.getElementById("docUsuario");
const btnBuscar = document.getElementById("filtrar");

inputBuscar.addEventListener("input", () => {
    if (inputBuscar.value.trim() === "") {
        btnBuscar.disabled = true;
    } else {
        btnBuscar.disabled = false;
    }
});

// Formulario de buscar usuario por ID
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

// Función para manejar la eliminación de un usuario
const manejarEliminacionUsuario = () => {
    document.querySelectorAll('.eliminarDocente, .eliminarEstudiante, .eliminarAdministrador').forEach(button => {
        button.addEventListener('click', function () {
            const form = this.closest('form');
            Swal.fire({
                title: '¿Estás seguro de inhabilitar este Usuario?',
                text: "Podrás habilitarlo nuevamente",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    const inputEliminar = document.createElement('input');
                    inputEliminar.type = 'hidden';
                    inputEliminar.name = 'actionDel';
                    inputEliminar.value = 'inhabilitarUsu';
                    form.appendChild(inputEliminar);
                    form.submit();
                }
            });
        });
    });
};

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const tipoUsuario = urlParams.get('tipoUsuario');

    if (tipoUsuario === 'estudiante') {
        document.getElementById('agregarDocenteBtn').style.display = 'none';
        if (agregarAdminBtn)
            agregarAdminBtn.style.display = 'none';
    } else if (tipoUsuario === 'administrador') {
        document.getElementById('agregarDocenteBtn').style.display = 'none';
        if (agregarAdminBtn)
            agregarAdminBtn.style.display = 'inline-block';
    } else {
        document.getElementById('agregarDocenteBtn').style.display = 'inline-block';
        if (agregarAdminBtn)
            agregarAdminBtn.style.display = 'none';
    }
    manejarEliminacionUsuario();
});

