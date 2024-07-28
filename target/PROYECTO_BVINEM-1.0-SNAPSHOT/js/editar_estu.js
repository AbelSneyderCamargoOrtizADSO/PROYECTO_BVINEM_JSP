/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


let titulo = document.querySelector(".header__title");
titulo.textContent = "Editar mis datos";

let form = document.getElementById("form");
const btn = document.querySelector(".formulario__boton");

btn.disabled = true;
const inputs = form.querySelectorAll(".formulario__input");

inputs.forEach(input => {
    input.addEventListener("input", () => btn.disabled = false);
});