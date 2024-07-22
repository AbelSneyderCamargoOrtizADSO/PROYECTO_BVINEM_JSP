/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// Poner título al header
let titulo = document.querySelector(".header__title");
titulo.textContent = "Gestión de Usuarios";

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

// Manejar modal
const manejarModal = () => {
    const modalDocente = document.getElementById("modalDocente");
    const modalEstudiante = document.getElementById("modalEstudiante");
    const spanDocente = modalDocente.querySelector(".modal__close");
    const spanEstudiante = modalEstudiante.querySelector(".modal__close");
    const formDocente = document.getElementById("formDocente");
    const formEstudiante = document.getElementById("formEstudiante");
    const btnAccionDocente = document.getElementById('btnAccionDocente');
    const btnAccionEstudiante = document.getElementById('btnAccionEstudiante');
    const inputBuscar = document.getElementById("docUsuario");
    const btnBuscar = document.getElementById("filtrar");

    // Función para deshabilitar botones
    const disableInputs = (inputs, btnAccion) => {
        const inputValor = Array.from(inputs).some(input => input.value.trim() !== "");
        if (inputValor) {
            btnAccion.removeAttribute('disabled');
            btnAccion.classList.remove("disable");
        } else {
            btnAccion.setAttribute('disabled', 'disabled');
            btnAccion.classList.add("disable");
        }
    };

    // Función para deshabilitar botón de buscar
    const disableBtnBuscar = () => {
        if (inputBuscar.value.length <= 0) {
            btnBuscar.setAttribute("disabled", "disabled");
        } else {
            btnBuscar.removeAttribute("disabled");
        }
    };

    // Eventos para inputs y botones
    formDocente.querySelectorAll('.form__input').forEach(input => input.addEventListener('input', () => disableInputs(formDocente.querySelectorAll('.form__input'), btnAccionDocente)));
    formEstudiante.querySelectorAll('.form__input').forEach(input => input.addEventListener('input', () => disableInputs(formEstudiante.querySelectorAll('.form__input'), btnAccionEstudiante)));
    inputBuscar.addEventListener("input", disableBtnBuscar);

    // Verificar el estado inicial de los botones al cargar la página
    disableInputs(formDocente.querySelectorAll('.form__input'), btnAccionDocente);
    disableInputs(formEstudiante.querySelectorAll('.form__input'), btnAccionEstudiante);
    disableBtnBuscar();

    // Función para abrir el modal para agregar docente
    document.getElementById("agregarDocenteBtn").onclick = () => {
        formDocente.reset();
        modalDocente.style.display = "block";
        btnAccionDocente.name = 'regDocente';
        document.getElementById("labelPassDocente").textContent = "Contraseña:";
        document.getElementById("contrasenaDocente").classList.add("obligatorio");
    };

    // Función para abrir el modal para agregar estudiante
//    document.getElementById("agregarEstudianteBtn").onclick = () => {
//        formEstudiante.reset();
//        modalEstudiante.style.display = "block";
//        btnAccionEstudiante.name = 'regEstudiante';
//        document.getElementById("labelPassEstudiante").textContent = "Contraseña:";
//        document.getElementById("contrasenaEstudiante").classList.add("obligatorio");
//    };

    // Función para cerrar el modal
    spanDocente.onclick = () => {
        modalDocente.style.display = "none";
    };
    spanEstudiante.onclick = () => {
        modalEstudiante.style.display = "none";
    };

    window.onclick = (event) => {
        if (event.target === modalDocente) {
            modalDocente.style.display = "none";
        } else if (event.target === modalEstudiante) {
            modalEstudiante.style.display = "none";
        }
    };
};

// Al dar click en el botón editar docente
function editarDocente(docUsu, nombre, apellido, correo) {
    const modal = document.getElementById("modalDocente");
    const formDocente = document.getElementById("formDocente");
    let inputIdDoc = document.getElementById('docenteId');
    if (!inputIdDoc) {
        inputIdDoc = document.createElement('input');
        inputIdDoc.type = 'hidden';
        inputIdDoc.name = 'userId';
        inputIdDoc.id = 'docenteId';
        formDocente.appendChild(inputIdDoc);
    }
    inputIdDoc.value = docUsu;

    document.getElementById("nuevoIdUserDocente").value = docUsu;
    document.getElementById("nombreDocente").value = nombre;
    document.getElementById("apellidoDocente").value = apellido;
    document.getElementById("correoDocente").value = correo;

    modal.style.display = "block";
    btnAccionDocente.name = 'editDocente';
    document.getElementById("labelPassDocente").textContent = "Nueva contraseña:";
    document.getElementById("contrasenaDocente").classList.remove("obligatorio");
}

// Al dar click en el botón editar estudiante
function editarEstudiante(docUsu, nombre, apellido, correo) {
    const modal = document.getElementById("modalEstudiante");
    const formEstudiante = document.getElementById("formEstudiante");
    let inputIdEst = document.getElementById('estudianteId');
    if (!inputIdEst) {
        inputIdEst = document.createElement('input');
        inputIdEst.type = 'hidden';
        inputIdEst.name = 'userId';
        inputIdEst.id = 'estudianteId';
        formEstudiante.appendChild(inputIdEst);
    }
    inputIdEst.value = docUsu;

    document.getElementById("nuevoIdUserEstudiante").value = docUsu;
    document.getElementById("nombreEstudiante").value = nombre;
    document.getElementById("apellidoEstudiante").value = apellido;
    document.getElementById("correoEstudiante").value = correo;

    modal.style.display = "block";
    btnAccionEstudiante.name = 'editEstudiante';
    document.getElementById("labelPassEstudiante").textContent = "Nueva contraseña:";
    document.getElementById("contrasenaEstudiante").classList.remove("obligatorio");
}

// Función para manejar la eliminación de un usuario
const manejarEliminacionUsuario = () => {
    document.querySelectorAll('.eliminarDocente, .eliminarEstudiante').forEach(button => {
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
                    inputEliminar.name = 'inhabilitarUsu';
                    inputEliminar.value = 'true';
                    form.appendChild(inputEliminar);
                    form.submit();
                }
            });
        });
    });
};

document.addEventListener('DOMContentLoaded', () => {
    manejarModal();
    manejarEliminacionUsuario();
});

