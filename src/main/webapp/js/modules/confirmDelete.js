/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const confirmar = (event, title, text, name, value) => {
    const button = event.target;  // Captura el botón que disparo el evento
    const form = button.closest("form");  // Encuentra el formulario mas cercano
    
    Swal.fire({
        title: title,
        text: text,
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
            inputEliminar.name = name;
            inputEliminar.value = value;
            form.appendChild(inputEliminar);
            form.submit();
        }
    });
};

export default confirmar;