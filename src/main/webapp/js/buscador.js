/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

import solicitud from './modules/ajax.js';

const resultadosContainer = document.getElementById('resultadosBusqueda');
const search = document.querySelector('.search');


const listar = async (query, tipo) => {
    let data = await solicitud(`sv_busqueda?query=${query}&tipo=${tipo}`);
    mostrarResultados(data, tipo);
};

search.addEventListener('submit', () => event.preventDefault());

search.addEventListener('input', function (event) {
    event.preventDefault();
    const query = document.getElementById("query").value;
    const tipo = document.getElementById("tipo").value;

    if (query.length > 0) {
        resultadosContainer.style.display = "flex";
        listar(query, tipo);
    } else {
        resultadosContainer.style.display = "none";
    }
});

function mostrarResultados(data, tipo) {

    // Limpiar resultados anteriores
    resultadosContainer.innerHTML = '';

    const fragment = document.createDocumentFragment();

    if (tipo === 'libros' && data.length > 0) {
        data.forEach(documento => {
            const resultadoItem = document.createElement('a');
            const titulo = document.createElement('p');
            const autor = document.createElement('span');
            const year = document.createElement('span');


            resultadoItem.classList.add("resultado");
            titulo.classList.add("resultado__title");
            autor.classList.add("resultado__autor");
            year.classList.add("resultado__year");

            resultadoItem.href = documento.archivoPDF;
            titulo.textContent = documento.titulo;
            autor.textContent = documento.autor;
            year.textContent = documento.year;


            resultadoItem.appendChild(titulo);
            resultadoItem.appendChild(autor);
            resultadoItem.appendChild(year);


            fragment.appendChild(resultadoItem);
        });
    } else if (tipo === 'foros' && data.length > 0) {
        data.forEach(foro => {
            const resultadoItem = document.createElement('a');
            const titulo = document.createElement('p');
            const id = document.createElement('span');

            resultadoItem.classList.add("resultado");
            id.classList.add("resultado__id");
            titulo.classList.add("resultado__title");

            resultadoItem.href = `mostrar_foro?id=${foro.id}`;
            id.textContent = foro.id;
            titulo.textContent = foro.titulo;

            resultadoItem.appendChild(id);
            resultadoItem.appendChild(titulo);

            fragment.appendChild(resultadoItem);
        });
    } else {
        const sinResultados = document.createElement('p');

        sinResultados.classList.add("empty");

        sinResultados.textContent = "No se encontraron resultados para la búsqueda.";
        fragment.appendChild(sinResultados);
    }

    resultadosContainer.appendChild(fragment);
}

