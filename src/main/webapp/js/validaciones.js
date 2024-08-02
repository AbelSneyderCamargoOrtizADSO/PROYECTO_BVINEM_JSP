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

    if (/[A-Za-zÁ-ÿ\s]/.test(event.key)) {
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

// LIMITAR CAMPOS
const limitedInputs = document.querySelectorAll('input[maxlength], textarea[maxlength]');

limitedInputs.forEach(input => {
    const limit = input.getAttribute('maxlength');

    input.addEventListener('input', () => {
        if (input.value.length > limit) {
            input.value = input.value.slice(0, limit);
        }
    });
});

// VALIDACION DE CAMPOS
document.addEventListener('DOMContentLoaded', function () {
    const formularios = document.querySelectorAll('.form__valid');

    formularios.forEach(formulario => {
        formulario.addEventListener('submit', function (event) {
            const campos = formulario.querySelectorAll('.obligatorio');
            const esValido = validarCampos(campos) && validarContrasenas(formulario);

            if (!esValido) {
                event.preventDefault();
            }
        });
    });

    function validarCampos(campos) {
        let formularioValido = true;

        // Limpiar mensajes de error anteriores
        document.querySelectorAll('.mensaje-error').forEach(mensaje => mensaje.remove());
        campos.forEach(campo => campo.classList.remove('error'));

        for (const campo of campos) {
            const valor = campo.value.trim();
            const nombreCampoName = campo.getAttribute('name');

            if (!valor) {
                mostrarError(campo, `El campo es obligatorio`);
                formularioValido = false;
            } else if (nombreCampoName === 'correo' && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor)) {
                mostrarError(campo, `El correo electronico no es válido`);
                formularioValido = false;
            }
        }
        return formularioValido;
    }

    function validarContrasenas(formulario) {
        const contrasena = formulario.querySelector('input[name="password"]');
        const confirmarContrasena = formulario.querySelector('input[name="confirmPass"]');

        // Limpiar mensajes de error anteriores
        document.querySelectorAll('.mensaje-error').forEach(mensaje => mensaje.remove());
        contrasena.classList.remove('error');
        confirmarContrasena.classList.remove('error');

        if (contrasena.value !== confirmarContrasena.value) {
            mostrarError(contrasena, 'Las contraseñas no coinciden');
            mostrarError(confirmarContrasena, 'Las contraseñas no coinciden');
            return false;
        }

        return true;
    }

    function mostrarError(campo, mensaje) {
        campo.classList.add('error');
        const mensajeError = document.createElement('span');
        mensajeError.classList.add('mensaje-error');
        mensajeError.textContent = mensaje;
        campo.parentElement.appendChild(mensajeError);
    }
});


// VALIDACIONES DE ARCHIVOS
const inputImage = document.getElementById('inputImagen');
const inputPDF = document.getElementById('inputPdf');


// IMAGENES
export let aprobImage = false;
inputImage.addEventListener('change', () => {
    const file = inputImage.files[0]; // Obtiene el primer archivo seleccionado
    if (file) {
        const extensiones = /(\.jpg|\.jpeg|\.png)$/i;
        const nombreArchivo = file.name;

        if (!extensiones.test(nombreArchivo)) {
            alert('Por favor, sube un archivo en formato .jpg, .jpeg, o .png.');
            inputImage.value = '';
            aprobImage = false;
        } else if (file.size > 1024 * 1024) {
            alert('El archivo es demasiado grande. Por favor, sube una imagen de máximo 1 MB.');
            inputImage.value = '';
            aprobImage = false;
        } else {
            aprobImage = true;
        }
    }
});

// ARCHIVOS 
inputPDF.addEventListener('change', () => {
    const file = inputPDF.files[0]; // Obtiene el primer archivo seleccionado
    if (file) {
        const extensiones = /(\.pdf)$/i;
        const nombreArchivo = file.name;
        if (!extensiones.test(nombreArchivo)) {
            alert('Por favor, sube un archivo en formato .pdf.');
            inputPDF.value = '';
        } else if (file.size > 3 * 1024 * 1024) { // 3 MB en bytes
            alert('El archivo es demasiado grande. Por favor, sube un documento de máximo 3 MB.');
            inputPDF.value = '';
        }
    }
});