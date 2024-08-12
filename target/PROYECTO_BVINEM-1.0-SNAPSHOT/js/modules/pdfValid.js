/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const validatePDF = (inputElement) => {
    if (inputElement) {
        inputElement.addEventListener('change', () => {
            const file = inputElement.files[0]; // Obtiene el primer archivo seleccionado
            if (file) {
                const extensiones = /(\.pdf)$/i;
                const nombreArchivo = file.name;

                if (!extensiones.test(nombreArchivo)) {
                    alert('Por favor, sube un archivo en formato .pdf.');
                    inputElement.value = '';
                } else if (file.size > 3 * 1024 * 1024) { // 3 MB en bytes
                    alert('El archivo es demasiado grande. Por favor, sube un documento de máximo 3 MB.');
                    inputElement.value = '';
                }
            }
        });
    }
};

export default validatePDF;

