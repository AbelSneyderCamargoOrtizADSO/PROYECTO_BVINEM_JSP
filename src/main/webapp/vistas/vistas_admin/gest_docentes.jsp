<%-- 
    Document   : gest_docente
    Created on : 16/07/2024, 2:34:48 a. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gestionar.css"/>
        <title>Gestionar docentes</title>
    </head>
    <body>

        <jsp:include page="header_admin.jsp" />

        <main>
            <section class="container">
                <a href="${pageContext.request.contextPath}/sv_foros" class="container__cerrar"><i class="bi bi-x-circle-fill"></i></a>

                <div class="container__top">
                    <button id="agregarDocenteBtn" class="button button--primary">Registrar Docente</button>

                    <!-- Formulario de busqueda -->
                    <form id="buscarUsuarioForm" method="get" action="${pageContext.request.contextPath}/sv_docente" class="form__doc">
                        <div class="form__group">
                            <input type="text" id="docUsuario" name="docDocente" class="form__input form__input--pd solo-numeros" placeholder="Buscar Docente por Documento">
                            <button type="submit" class="button button--primary button--icon" id="filtrar" disabled>
                                <svg viewBox="0 0 24 24" aria-hidden="true" class="svg-icon">
                                <g>
                                <path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path>
                                </g>
                                </svg>
                            </button>
                        </div>
                        <button type="button" class="button button--secondary" id="resetBuscar">Restablecer</button>
                    </form>
                </div>

                <table id="tablaDocentes" class="table">
                    <thead class="table__head">
                        <tr class="table__row">
                            <th class="table__header">Documento de usuario</th>
                            <th class="table__header">Nombres</th>
                            <th class="table__header">Apellidos</th>
                            <th class="table__header">Correo</th>
                            <th class="table__header">Fecha de registro</th>
                            <th class="table__header">Acciones</th>
                        </tr>
                    </thead>
                    <tbody class="table__body">
                        <c:forEach var="docente" items="${docentes}">
                            <tr class="table__row">
                                <td class="table__cell" data-label="Documento de usuario">${docente.docUsu}</td>
                                <td class="table__cell" data-label="Nombres">${docente.nombre}</td>
                                <td class="table__cell" data-label="Apellidos">${docente.apellido}</td>
                                <td class="table__cell" data-label="Correo">${docente.correo}</td>
                                <td class="table__cell" data-label="Fecha de registro">${docente.fechaRegistro}</td>
                                <td class="table__cell table__cell-display"  data-label="Acciones">
                                    <button class="button button--edit" onclick="editarDocente('${docente.docUsu}', '${docente.nombre}', '${docente.apellido}', '${docente.correo}', '${docente.pass}')">Editar</button>
                                    <form action="${pageContext.request.contextPath}/sv_docente" method="POST">
                                        <input type="hidden" name="docDocente" value="${docente.docUsu}">
                                        <button type="button" class="button button--delete eliminarDocente">Eliminar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination" class="pagination"></div>
            </section>

            <div id="modalDocente" class="modal">
                <div class="modal__content">
                    <span class="modal__close">&times;</span>
                    <h2 id="modalTitulo" class="modal__title">Registrar Docente</h2>
                    <form action="${pageContext.request.contextPath}/sv_docente" method="POST" id="formDocente" class="form">
                        <label for="nuevoIdUser" class="form__label">Documento de Usuario:</label>
                        <input type="text" id="nuevoIdUser" class="form__input solo-numeros" name="nuevoDocenteId" >
                        <label for="nombre" class="form__label">Nombres:</label>
                        <input type="text" id="nombre" class="form__input solo-letras" name="nombres" >
                        <label for="apellido" class="form__label">Apellidos:</label>
                        <input type="text" id="apellido" class="form__input solo-letras" name="apellidos" >
                        <label for="correo" class="form__label">Correo:</label>
                        <input type="email" id="correo" class="form__input" name="correo" >
                        <label for="contrasena" class="form__label">Contraseña:</label>
                        <input type="password" id="contrasena" class="form__input" name="password" >
                        <button type="submit" class="button button--primary form__button" name="regDocente" id="btnReg">Guardar</button>
                        <button type="submit" class="button button--primary form__button" name="editDocente" id="btnEdit">Guardar Cambios</button>
                    </form>
                </div>
            </div>
        </main>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
                                        // Mostrar SweetAlert si hay un mensaje de error en la sesión
                                        window.addEventListener('load', function () {
                                            const errorMessage = '<%= session.getAttribute("error")%>';
                                            if (errorMessage && errorMessage !== 'null') {
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Error',
                                                    text: errorMessage,
                                                });
            <% session.removeAttribute("error");%>
                                            }

                                            const successMessage = '<%= session.getAttribute("success")%>';
                                            if (successMessage && successMessage !== 'null') {
                                                Swal.fire({
                                                    icon: 'success',
                                                    title: 'Éxito',
                                                    text: successMessage,
                                                });
            <% session.removeAttribute("success");%>
                                            }
                                        });

                                        document.getElementById('resetBuscar').addEventListener('click', function () {
                                            window.location.href = '${pageContext.request.contextPath}/sv_docente';
                                        });
        </script>
        <script src="js/gestionar.js"></script>
        <script src="js/validaciones.js"></script>
        <script src="js/pagination.js"></script>
    </body>
</html>
