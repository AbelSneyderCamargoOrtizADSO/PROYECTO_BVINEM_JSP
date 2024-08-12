/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
export let aprobImage = false;

const validateImage = (inputElement) => {
    if (inputElement) {
        inputElement.addEventListener('change', () => {
            const file = inputElement.files[0]; // Obtiene el primer archivo seleccionado
            if (file) {
                const extensiones = /(\.jpg|\.jpeg|\.png)$/i;
                const nombreArchivo = file.name;

                if (!extensiones.test(nombreArchivo)) {
                    alert('Por favor, sube un archivo en formato .jpg, .jpeg, o .png.');
                    inputElement.value = '';
                    aprobImage = false;
                } else if (file.size > 1024 * 1024) {
                    alert('El archivo es demasiado grande. Por favor, sube una imagen de máximo 1 MB.');
                    inputElement.value = '';
                    aprobImage = false;
                } else {
                    aprobImage = true;
                }
            }
        });
    }
};

export default validateImage;


