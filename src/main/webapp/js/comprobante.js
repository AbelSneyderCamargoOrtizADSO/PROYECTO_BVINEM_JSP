/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// VARIABLES
let fecha = document.getElementById("fechaRegistro");
let rol = document.getElementById("rol");

if (rol.dataset.rol === "2") {
    rol.textContent = "Docente";
} else if (rol.dataset.rol === "3") {
    rol.textContent = "Administrador";
}

const ahora = new Date();
const fechaHoraFormateada = ahora.toLocaleString('es-ES');

fecha.textContent = fechaHoraFormateada;
