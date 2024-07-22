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

// VALIDACION DE CAMPOS
document.addEventListener('DOMContentLoaded', function () {
    const formularios = document.querySelectorAll('.form__valid');

    formularios.forEach(formulario => {
        formulario.addEventListener('submit', function (event) {
            const campos = formulario.querySelectorAll('.obligatorio');
            const esValido = validarCampos(campos);

            if (!esValido) {
                event.preventDefault(); 
            }
        });
    });

    function validarCampos(campos) {
        for (const campo of campos) {
            const valor = campo.value.trim();
            const nombreCampo = campo.previousElementSibling ? campo.previousElementSibling.textContent : campo.getAttribute('name');
            const nombreCampoName = campo.getAttribute('name');
            
            if (!valor) {
                Swal.fire({
                    icon: 'error',
                    title: 'Campo vacío',
                    text: `El campo ${nombreCampo} es obligatorio.`,
                });
                return false; // Detiene la validación y muestra el mensaje de error
            }
            
            if (nombreCampoName === 'correo' && !validarEmail(valor)) {
                Swal.fire({
                    icon: 'error',
                    title: 'Correo inválido',
                    text: `El campo ${nombreCampo} no es un correo válido.`,
                });
                return false; // Detiene la validación y muestra el mensaje de error
            }
        }
        return true; // Si todos los campos estan completos, retorna true
    }

    function validarEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }
});
