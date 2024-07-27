/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
    const rowsPerPage = 8;
    const maxButtonsToShow = 3; // Número de botones a mostrar siempre
    const tables = document.querySelectorAll('.table');

    tables.forEach(table => {
        const rows = Array.from(table.querySelectorAll('tbody tr'));
        const pagination = document.createElement('div');
        pagination.classList.add('pagination');
        table.parentNode.insertBefore(pagination, table.nextSibling);

        const totalPages = Math.ceil(rows.length / rowsPerPage);
        let currentPage = 1;

        function displayRows(page) {
            const start = (page - 1) * rowsPerPage;
            const end = start + rowsPerPage;

            rows.forEach((row, index) => {
                row.style.display = (index >= start && index < end) ? 'table-row' : 'none';
            });
        }

        function createPageButton(page) {
            const btn = document.createElement('button');
            btn.textContent = page;
            btn.classList.add('pagination__button');
            if (page === currentPage)
                btn.classList.add('active');
            btn.addEventListener('click', () => {
                currentPage = page;
                displayRows(page);
                updatePageButtons();
            });
            return btn;
        }

        function updatePageButtons() {
            pagination.innerHTML = '';

            const prevButton = document.createElement('button');
            prevButton.textContent = '<';
            prevButton.classList.add('pagination__button');
            prevButton.disabled = currentPage === 1;
            prevButton.addEventListener('click', () => {
                if (currentPage > 1) {
                    currentPage--;
                    displayRows(currentPage);
                    updatePageButtons();
                }
            });
            pagination.appendChild(prevButton);

            let startPage = Math.max(1, currentPage - Math.floor(maxButtonsToShow / 2));
            let endPage = startPage + maxButtonsToShow - 1;

            if (endPage > totalPages) {
                endPage = totalPages;
                startPage = Math.max(1, endPage - maxButtonsToShow + 1);
            }

            for (let i = startPage; i <= endPage; i++) {
                const btn = createPageButton(i);
                pagination.appendChild(btn);
            }

            const nextButton = document.createElement('button');
            nextButton.textContent = '>';
            nextButton.classList.add('pagination__button');
            nextButton.disabled = currentPage === totalPages;
            nextButton.addEventListener('click', () => {
                if (currentPage < totalPages) {
                    currentPage++;
                    displayRows(currentPage);
                    updatePageButtons();
                }
            });
            pagination.appendChild(nextButton);
        }

        displayRows(currentPage);
        updatePageButtons();
    });

    function togglePagination() {
        tables.forEach(table => {
            const pagination = table.nextElementSibling;
            if (table.style.display === 'none') {
                pagination.style.display = 'none';
            } else {
                pagination.style.display = 'flex';
            }
        });
    }

    togglePagination();
});
