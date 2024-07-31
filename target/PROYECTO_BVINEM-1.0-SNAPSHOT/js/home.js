/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// EVITAR ENVIO DE FORMULARIO CON FILTROS VACIOS
document.getElementById('filter__form').addEventListener('submit', function (event) {
    // Obtener todos los select del formulario
    let selects = this.querySelectorAll('select'); // Aquí, this hace referencia al elemento al que se adjunto el evento addEventListener (es decir el formulario) 

    // Iterar sobre los select y eliminar aquellos que están vacíos
    selects.forEach(select => {
        if (!select.value) {
            select.name = '';  // Eliminar el nombre del campo para que no sea enviado
        }
    });
});

document.querySelectorAll('.eliminarDoc').forEach(button => {
    button.addEventListener('click', function () {
        const form = this.closest('form');
        Swal.fire({
            title: '¿Estás seguro de eliminar este libro?',
            text: "Esta opción no es reversible",
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
                inputEliminar.name = 'eliminarDocumento';
                inputEliminar.value = 'true';
                form.appendChild(inputEliminar);
                form.submit();
            }
        });
    });
});

