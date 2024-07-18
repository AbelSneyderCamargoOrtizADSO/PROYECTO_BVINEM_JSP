/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// Función para permitir solo números
const soloNumeros = (event) => {
    // El método .includes(event.key) se utiliza para verificar si una tecla específica (representada por event.key) está presente en un array de teclas permitidas. 
    if (['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown', 'Home', 'End', 'Tab'].includes(event.key)) {
        return;
    }

    if (/[0-9]/.test(event.key)) {
        return;
    }

    event.preventDefault();
};

// Función para permitir solo letras
const soloLetras = (event) => {
    if (['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown', 'Home', 'End', 'Tab'].includes(event.key)) {
        return;
    }

    if (/[A-Za-z\s]/.test(event.key)) {
        return;
    }

    event.preventDefault();
};

// Añadir el evento 'keydown' a todos los elementos con la clase 'solo-numeros'
document.querySelectorAll('.solo-numeros').forEach(input => {
    input.addEventListener('keydown', soloNumeros);
});

// Añadir el evento 'keydown' a todos los elementos con la clase 'solo-letras'
document.querySelectorAll('.solo-letras').forEach(input => {
    input.addEventListener('keydown', soloLetras);
});

