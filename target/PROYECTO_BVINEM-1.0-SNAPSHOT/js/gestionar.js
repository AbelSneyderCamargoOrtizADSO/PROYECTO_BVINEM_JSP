/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const agregarAdminBtn = document.getElementById("agregarAdminBtn");

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
    const modalAdmin = document.getElementById("modalAdmin");
    const spanDocente = modalDocente.querySelector(".modal__close");
    const spanEstudiante = modalEstudiante.querySelector(".modal__close");
    const spanAdmin = modalAdmin?.querySelector(".modal__close");
    const formDocente = document.getElementById("formDocente");
    const formEstudiante = document.getElementById("formEstudiante");
    const formAdmin = document.getElementById("formAdmin");
    const btnAccionDocente = document.getElementById('btnAccionDocente');
    const btnAccionEstudiante = document.getElementById('btnAccionEstudiante');
    const btnAccionAdmin = document.getElementById('btnAccionAdmin');
    const inputBuscar = document.getElementById("docUsuario");
    const btnBuscar = document.getElementById("filtrar");
    const inputs = document.querySelectorAll('.form__input');
    
    inputs.forEach(input => {
        input.addEventListener("input", () => {
            [btnAccionDocente, btnAccionEstudiante, btnAccionAdmin].forEach(btn => {
                if (btn && btn.disabled) btn.disabled = false;
            });
        });
    });
    
    inputBuscar.addEventListener("input", () => {
        if(inputBuscar.value.trim() === ""){
            btnBuscar.disabled = true;
        } else{
            btnBuscar.disabled = false;
        }
    });
 
    document.getElementById("agregarDocenteBtn").onclick = () => {
        formDocente.reset();
        modalDocente.style.display = "block";
        btnAccionDocente.name = 'regDocente';
        document.getElementById("labelPassDocente").textContent = "Contraseña:";
        document.getElementById("modalTituloDocente").textContent = "Registrar docente";
        document.getElementById("contrasenaDocente").classList.add("obligatorio");
    };
    
    if(agregarAdminBtn){
        agregarAdminBtn.onclick = () => {
            formAdmin.reset();
            modalAdmin.style.display = "block";
            btnAccionAdmin.name = 'regAdmin';
            document.getElementById("labelPassAdmin").textContent = "Contraseña:";
            document.getElementById("modalTituloAdmin").textContent = "Registrar Administrador";
            document.getElementById("contrasenaAdmin").classList.add("obligatorio");
        };
    }

    spanDocente.onclick = () => {
        modalDocente.style.display = "none";
    };
    spanEstudiante.onclick = () => {
        modalEstudiante.style.display = "none";
    };
    if(spanAdmin){
        spanAdmin.onclick = () => {
            modalAdmin.style.display = "none";
        };
    }
    

    window.onclick = (event) => {
        if (event.target === modalDocente) {
            modalDocente.style.display = "none";
            btnAccionDocente.disabled = true;
        } else if (event.target === modalEstudiante) {
            modalEstudiante.style.display = "none";
            btnAccionEstudiante.disabled = true;
        } else if (event.target === modalAdmin) {
            modalAdmin.style.display = "none";
            btnAccionAdmin.disabled = true;
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
    document.getElementById("modalTituloDocente").textContent = "Editar docente";
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

function editarAdministrador(docUsu, nombre, apellido, correo) {
    const modal = document.getElementById("modalAdmin");
    const formAdmin = document.getElementById("formAdmin");
    let inputIdAdmin = document.getElementById('adminId');
    if (!inputIdAdmin) {
        inputIdAdmin = document.createElement('input');
        inputIdAdmin.type = 'hidden';
        inputIdAdmin.name = 'userId';
        inputIdAdmin.id = 'adminId';
        formAdmin.appendChild(inputIdAdmin);
    }
    inputIdAdmin.value = docUsu;

    document.getElementById("nuevoIdUserAdmin").value = docUsu;
    document.getElementById("nombreAdmin").value = nombre;
    document.getElementById("apellidoAdmin").value = apellido;
    document.getElementById("correoAdmin").value = correo;

    modal.style.display = "block";
    document.getElementById('btnAccionAdmin').name = 'editAdmin';
    document.getElementById("labelPassAdmin").textContent = "Nueva contraseña:";
    document.getElementById("modalTituloAdmin").textContent = "Editar Administrador";
    document.getElementById("contrasenaAdmin").classList.remove("obligatorio");
}

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
        if(agregarAdminBtn) agregarAdminBtn.style.display = 'none';
    } else if (tipoUsuario === 'administrador') {
        document.getElementById('agregarDocenteBtn').style.display = 'none';
        if(agregarAdminBtn) agregarAdminBtn.style.display = 'inline-block';
    } else {
        document.getElementById('agregarDocenteBtn').style.display = 'inline-block';
        if(agregarAdminBtn) agregarAdminBtn.style.display = 'none';
    }
    manejarModal();
    manejarEliminacionUsuario();
});

