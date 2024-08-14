<%-- 
    Document   : gest_docente
    Created on : 16/07/2024, 2:34:48 a. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Desactivar la caché del navegador, asegurando que siempre se solicite una versión nueva de la página desde el servidor
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies  
%>
<!DOCTYPE html>
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
                <a href="${pageContext.request.contextPath}/sv_foros" class="cerrar"><i class="bi bi-x-circle-fill"></i></a>

                <div class="container__top">
                    <div class="top__btns">
                        <a href="${pageContext.request.contextPath}/sv_usuario?tipoUsuario=docente" class="btn__link">Docentes</a>
                        <a href="${pageContext.request.contextPath}/sv_usuario?tipoUsuario=estudiante" class="btn__link btn__link--green">Estudiantes</a>
                        <c:if test="${rol == '4'}">
                            <a href="${pageContext.request.contextPath}/sv_usuario?tipoUsuario=administrador" class="btn__link btn__link--blue">Administradores</a>
                        </c:if>
                    </div>
                    <a href="${pageContext.request.contextPath}/sv_gestUsuario?action=register&rol=docente" id="agregarDocenteBtn" class="btn__link"><img src="assets/adduser.png" alt="alt"/></a>
                        <c:if test="${rol == '4'}">
                        <a href="${pageContext.request.contextPath}/sv_gestUsuario?action=register&rol=administrador" id="agregarAdminBtn" class="btn__link btn__link--blue"><img src="assets/adduser.png" alt="alt"/></a>
                        </c:if>

                    <!-- Formulario de busqueda -->
                    <form id="buscarUsuarioForm" method="get" action="${pageContext.request.contextPath}/sv_usuario" class="form__search">
                        <div class="form__group">
                            <input type="text" id="docUsuario" name="docUsuario" class="form__input form__input--pd solo-numeros" placeholder="Buscar Usuario por Documento">
                            <button type="submit" class="form__btn button--icon" id="filtrar" disabled>
                                <svg viewBox="0 0 24 24" aria-hidden="true" class="svg-icon">
                                <g>
                                <path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path>
                                </g>
                                </svg>
                            </button>
                        </div>
                        <a href="${pageContext.request.contextPath}/sv_usuario?tipoUsuario=${tipoUsuario}" class="btn__link btn__link--white" id="resetBuscar">Restablecer</a>
                        <input type="hidden" name="tipoUsuario" value="${tipoUsuario}">
                    </form>
                </div>


                <table id="tablaUsuarios" class="table">
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
                            <tr class="table__row" data-rol="${usuario.rol}">
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
                                            <p class="inhabilitado">Inhabilitado</p>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="table__cell table__cell-display" data-label="Acciones">
                                    <a href="${pageContext.request.contextPath}/sv_gestUsuario?action=edit&rol=${tipoUsuario}&idUser=${usuario.docUsu}" class="table__btn btn--hover table__btn--blue">Editar</a>
                                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST">
                                        <input type="hidden" name="docUsuario" value="${usuario.docUsu}">
                                        <input type="hidden" name="tipoUsuario" value="${tipoUsuario}">
                                        <c:choose>
                                            <c:when test="${usuario.estadoId == 1}">
                                                <button type="button" class="table__btn btn--hover table__btn--red eliminarUsuario">Inhabilitar</button>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="hidden" name="actionDel" value="habilitarUsu">
                                                <button type="submit" class="table__btn btn--hover table__btn--yellow">Habilitar</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
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
        </script>
        <script src="js/gestionar.js" type="module"></script>
    </body>
</html>
