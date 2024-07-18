/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
    const rowsPerPage = 4;
    const table = document.getElementById('tablaDocentes');
    const rows = Array.from(table.querySelectorAll('tbody tr'));
    const pagination = document.getElementById('pagination');
    const totalPages = Math.ceil(rows.length / rowsPerPage);

    function displayRows(page) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        rows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? 'table-row' : 'none';
        });
    }

    function setupPagination() {
        pagination.innerHTML = '';
        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.classList.add('pagination__button');
            btn.addEventListener('click', () => {
                displayRows(i);
                document.querySelectorAll('.pagination__button').forEach(button => {
                    button.classList.remove('active');
                });
                btn.classList.add('active');
            });
            pagination.appendChild(btn);
        }

        // Seleccionar la primera página por defecto
        if (pagination.firstChild) {
            pagination.firstChild.classList.add('active');
        }
    }

    setupPagination();
    displayRows(1); // Mostrar la primera página por defecto
});
