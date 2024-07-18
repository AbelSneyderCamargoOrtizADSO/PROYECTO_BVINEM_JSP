/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// PONER TITULO AL HEADER
let titulo = document.querySelector(".header__title");
titulo.textContent = "Gestion de Docentes";

// FORMULARIO DE BUSCAR DOCENTE POR ID
// Limpiarlo
document.getElementById('buscarUsuarioForm').addEventListener('submit', function (event) {
    // Obtener todos los select del formulario
    let inputs = this.querySelectorAll('input'); // Aquí, this hace referencia al elemento al que se adjunto el evento addEventListener (es decir el formulario) 

    // Iterar sobre los select y eliminar aquellos que están vacíos
    inputs.forEach(inputs => {
        if (!inputs.value) {
            inputs.name = '';  // Eliminar el nombre del campo para que no sea enviado
        }
    });
});

// MODAL
document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById("modalDocente");
    const span = document.querySelector(".modal__close");
    const modalTitulo = document.getElementById("modalTitulo");
    const formDocente = document.getElementById("formDocente");

    const inputs = formDocente.querySelectorAll('.form__input');
    const btnReg = document.getElementById('btnReg');
    const btnEdit = document.getElementById('btnEdit');

    // Deshabilitar boton si los inputs del formulario no han sido modificados
    function disableInputs() {
        let inputvalor = false;

        inputs.forEach(input => {
            if (input.value.trim() !== "") {
                inputvalor = true;
            }
        });

        if (inputvalor) {
            btnReg.removeAttribute('disabled');
            btnEdit.removeAttribute('disabled');
        } else {
            btnReg.setAttribute('disabled', 'disabled');
            btnEdit.setAttribute('disabled', 'disabled');
        }
    }

    inputs.forEach(input => {
        input.addEventListener('input', disableInputs);
    });

    // Verificar el estado inicial de los botones al cargar la página
    disableInputs();

    // Deshabilitar boton si es input esta vacio
    let inputBuscar = document.getElementById("docUsuario");
    let btnBuscar = document.getElementById("filtrar");

    function disableBtn() {
        if (inputBuscar.value.length <= 0) {
            btnBuscar.setAttribute("disabled", "disabled");
        } else {
            btnBuscar.removeAttribute("disabled");
        }
    }
    inputBuscar.addEventListener("input", disableBtn);
    disableBtn();


    document.getElementById("agregarDocenteBtn").onclick = () => {
        formDocente.reset();
        modalTitulo.textContent = "Registrar Docente";
        modal.style.display = "block";
        document.getElementById("btnReg").style.display = 'block';
        document.getElementById("btnEdit").style.display = 'none';
    };

    span.onclick = () => {
        modal.style.display = "none";
    };

    window.onclick = (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
        ;
    };
});

function editarDocente(docUsu, nombre, apellido, correo, pass) {
    const modal = document.getElementById("modalDocente");
    const modalTitulo = document.getElementById("modalTitulo");
    const formDocente = document.getElementById("formDocente");
    const inputIdDoc = document.createElement('input');
    inputIdDoc.type = 'hidden';
    inputIdDoc.name = 'docenteId';
    inputIdDoc.id = 'docenteId';
    inputIdDoc.value = 'docUsu';
    formDocente.appendChild(inputIdDoc);

    document.getElementById("docenteId").value = docUsu;
    document.getElementById("nuevoIdUser").value = docUsu;
    document.getElementById("nombre").value = nombre;
    document.getElementById("apellido").value = apellido;
    document.getElementById("correo").value = correo;
    const contrasenaInput = document.getElementById("contrasena");
    contrasenaInput.type = 'text';
    contrasenaInput.value = pass;

    // Evento para cambiar el tipo a password al escribir
    contrasenaInput.addEventListener('input', () => {
        contrasenaInput.type = 'password';
    });

    modalTitulo.textContent = "Editar Docente";
    modal.style.display = "block";
    document.getElementById("btnReg").style.display = 'none';
    document.getElementById("btnEdit").style.display = 'block';
}

document.querySelectorAll('.eliminarDocente').forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form'); // Encuentra el formulario más cercano

        Swal.fire({
            title: '¿Estás seguro de eliminar este Docente?',
            text: "¡Esta acción no se puede revertir!",
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
                inputEliminar.name = 'eliminarDocente';
                inputEliminar.value = 'true';
                form.appendChild(inputEliminar);

                form.submit();
            }
        });
    });
});
