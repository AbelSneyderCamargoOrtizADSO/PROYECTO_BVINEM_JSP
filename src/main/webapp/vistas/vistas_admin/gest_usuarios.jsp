<%-- 
    Document   : gest_docente
    Created on : 16/07/2024, 2:34:48 a. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%
    if (session == null || session.getAttribute("logueado") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gestionar.css"/>
        <title>Gestionar Usuarios</title>
    </head>
    <body>

        <jsp:include page="header_admin.jsp" />

        <main>
            <section class="container">
                <a href="${pageContext.request.contextPath}/sv_foros" class="container__cerrar"><i class="bi bi-x-circle-fill"></i></a>

                <div class="container__top">
                    <button id="mostrarDocentesBtn" class="button button--primary">Mostrar Docentes</button>
                    <button id="mostrarEstudiantesBtn" class="button button--primary">Mostrar Estudiantes</button>
                    <button id="agregarDocenteBtn" class="button button--primary">Registrar Docente</button>
                    <!--<button id="agregarEstudianteBtn" class="button button--primary">Registrar Estudiante</button>-->

                    <!-- Formulario de busqueda -->
                    <form id="buscarUsuarioForm" method="get" action="${pageContext.request.contextPath}/sv_usuario" class="form__search">
                        <div class="form__group">
                            <input type="text" id="docUsuario" name="docUsuario" class="form__input form__input--pd solo-numeros" placeholder="Buscar Usuario por Documento">
                            <button type="submit" class="button button--primary button--icon" id="filtrar">
                                <svg viewBox="0 0 24 24" aria-hidden="true" class="svg-icon">
                                <g>
                                <path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path>
                                </g>
                                </svg>
                            </button>
                        </div>
                        <button type="button" class="button button--secondary" id="resetBuscar">Restablecer</button>
                        <input type="hidden" name="tipoUsuario" value="${tipoUsuario}">
                    </form>
                </div>

                <div id="contenedorTablas">
                    <table id="tablaDocentes" class="table" style="${tipoUsuario == 'docente' ? '' : 'display:none;'}">
                        <thead class="table__head">
                            <tr class="table__row">
                                <th class="table__header">Documento de usuario</th>
                                <th class="table__header">Nombres</th>
                                <th class="table__header">Apellidos</th>
                                <th class="table__header">Correo</th>
                                <th class="table__header">Fecha de registro</th>
                                <th class="table__header">Estado</th>
                                <th class="table__header">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="table__body">
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr class="table__row">
                                    <td class="table__cell" data-label="Documento de usuario">${usuario.docUsu}</td>
                                    <td class="table__cell" data-label="Nombres">${usuario.nombre}</td>
                                    <td class="table__cell" data-label="Apellidos">${usuario.apellido}</td>
                                    <td class="table__cell" data-label="Correo">${usuario.correo}</td>
                                    <td class="table__cell" data-label="Fecha de registro">${usuario.fechaRegistro}</td>
                                    <td class="table__cell" data-label="Estado">
                                        <c:choose>
                                            <c:when test="${usuario.estadoId == 1}">
                                                <p>Habilitado</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="delete">Inhabilitado</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="table__cell table__cell-display" data-label="Acciones">
                                        <button class="table__btn button button--edit" onclick="editarDocente('${usuario.docUsu}', '${usuario.nombre}', '${usuario.apellido}', '${usuario.correo}', '${tipoUsuario}')">Editar</button>
                                        <form action="${pageContext.request.contextPath}/sv_usuario" method="POST">
                                            <input type="hidden" name="docDocente" value="${usuario.docUsu}">
                                            <input type="hidden" name="tipoUsuario" value="docente">
                                            <c:choose>
                                                <c:when test="${usuario.estadoId == 1}">
                                                    <button type="button" class="table__btn button button--delete eliminarDocente">Inhabilitar</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" class="table__btn button button--enable" name="habilitarUsu">Habilitar</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <table id="tablaEstudiantes" class="table" style="${tipoUsuario == 'estudiante' ? '' : 'display:none;'}">
                        <thead class="table__head">
                            <tr class="table__row">
                                <th class="table__header">Documento de usuario</th>
                                <th class="table__header">Nombres</th>
                                <th class="table__header">Apellidos</th>
                                <th class="table__header">Correo</th>
                                <th class="table__header">Fecha de registro</th>
                                <th class="table__header">Estado</th>
                                <th class="table__header">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="table__body">
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr class="table__row">
                                    <td class="table__cell" data-label="Documento de usuario">${usuario.docUsu}</td>
                                    <td class="table__cell" data-label="Nombres">${usuario.nombre}</td>
                                    <td class="table__cell" data-label="Apellidos">${usuario.apellido}</td>
                                    <td class="table__cell" data-label="Correo">${usuario.correo}</td>
                                    <td class="table__cell" data-label="Fecha de registro">${usuario.fechaRegistro}</td>
                                    <td class="table__cell" data-label="Estado">
                                        <c:choose>
                                            <c:when test="${usuario.estadoId == 1}">
                                                <p>Habilitado</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="delete">Inhabilitado</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="table__cell table__cell-display" data-label="Acciones">
                                        <button class="table__btn button button--edit" onclick="editarEstudiante('${usuario.docUsu}', '${usuario.nombre}', '${usuario.apellido}', '${usuario.correo}', '${tipoUsuario}')">Editar</button>
                                        <form action="${pageContext.request.contextPath}/sv_usuario" method="POST">
                                            <input type="hidden" name="docEstudiante" value="${usuario.docUsu}">
                                            <input type="hidden" name="tipoUsuario" value="estudiante">
                                            <c:choose>
                                                <c:when test="${usuario.estadoId == 1}">
                                                    <button type="button" class="table__btn button button--delete eliminarEstudiante">Inhabilitar</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" class="table__btn button button--enable" name="habilitarUsu">Habilitar</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div id="pagination" class="pagination"></div>
            </section>

            <div id="modalDocente" class="modal">
                <div class="modal__content">
                    <span class="modal__close">&times;</span>
                    <h2 id="modalTituloDocente" class="modal__title">Registrar Docente</h2>
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" id="formDocente" class="form form__valid">
                        <label for="nuevoIdUserDocente" class="form__label">Documento de Usuario</label>
                        <input type="text" id="nuevoIdUserDocente" class="form__input solo-numeros obligatorio" name="nuevoDoc">

                        <label for="nombreDocente" class="form__label">Nombres</label>
                        <input type="text" id="nombreDocente" class="form__input solo-letras obligatorio" name="nombres">

                        <label for="apellidoDocente" class="form__label">Apellidos</label>
                        <input type="text" id="apellidoDocente" class="form__input solo-letras obligatorio" name="apellidos">

                        <label for="correoDocente" class="form__label">Correo</label>
                        <input id="correoDocente" class="form__input obligatorio" name="correo">

                        <label for="contrasenaDocente" class="form__label" id="labelPassDocente">Contraseña</label>
                        <input type="password" id="contrasenaDocente" class="form__input obligatorio" name="password">

                        <input type="hidden" name="regDocente" value="true">
                        <button type="submit" class="button button--primary form__button" id="btnAccionDocente">Guardar</button>
                    </form>
                </div>
            </div>
                        
                       
            <div id="modalEstudiante" class="modal">
                <div class="modal__content">
                    <span class="modal__close">&times;</span>
                    <h2 id="modalTituloEstudiante" class="modal__title">Registrar Estudiante</h2>
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" id="formEstudiante" class="form form__valid">
                        <label for="nuevoIdUserEstudiante" class="form__label">Documento de Usuario</label>
                        <input type="text" id="nuevoIdUserEstudiante" class="form__input solo-numeros obligatorio" name="nuevoDoc">

                        <label for="nombreEstudiante" class="form__label">Nombres</label>
                        <input type="text" id="nombreEstudiante" class="form__input solo-letras obligatorio" name="nombres">

                        <label for="apellidoEstudiante" class="form__label">Apellidos</label>
                        <input type="text" id="apellidoEstudiante" class="form__input solo-letras obligatorio" name="apellidos">

                        <label for="correoEstudiante" class="form__label">Correo</label>
                        <input id="correoEstudiante" class="form__input obligatorio" name="correo">

                        <label for="contrasenaEstudiante" class="form__label" id="labelPassEstudiante">Contraseña</label>
                        <input type="password" id="contrasenaEstudiante" class="form__input obligatorio" name="password">

                        <input type="hidden" name="regEstudiante" value="true">
                        <button type="submit" class="button button--primary form__button" id="btnAccionEstudiante">Guardar</button>
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
                window.location.href = '${pageContext.request.contextPath}/sv_usuario';
            });

            // Alternar entre tablas de docentes y estudiantes
            document.getElementById('mostrarDocentesBtn').addEventListener('click', function () {
                window.location.href = `${pageContext.request.contextPath}/sv_usuario?tipoUsuario=docente`;
            });

            document.getElementById('mostrarEstudiantesBtn').addEventListener('click', function () {
                window.location.href = `${pageContext.request.contextPath}/sv_usuario?tipoUsuario=estudiante`;
            });
        </script>

        <script src="js/gestionar.js"></script>
        <script src="js/validaciones.js"></script>
        <script src="js/pagination.js"></script>
    </body>
</html>
