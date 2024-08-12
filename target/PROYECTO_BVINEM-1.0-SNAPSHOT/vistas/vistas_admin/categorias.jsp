<%-- 
    Document   : categorias
    Created on : 5/08/2024, 10:08:40 p. m.
    Author     : Abelito
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Desactivar la caché del navegador, asegurando que siempre se solicite una versión nueva de la página desde el servidor
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies  
%>

<!DOCTYPE html>
<html>
    <jsp:include page="header_admin.jsp" />
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categorias.css"/>
        <title>Editar categorías</title>
    </head>
    <body>
        <main>
            <section class="container">
                <div class="container__top">
                    <a href="${pageContext.request.contextPath}/sv_categorias?categoria=asignatura" class="container__btn btn--third">Listar Asignaturas</a>
                    <a href="${pageContext.request.contextPath}/sv_categorias?categoria=idioma" class="container__btn btn--third">Listar Idiomas</a>
                    <a href="${pageContext.request.contextPath}/sv_categorias?categoria=tipo+foro" class="container__btn btn--third">Listar Tipos de foro</a>
                    <a href="${pageContext.request.contextPath}/sv_categorias?categoria=tipo+documento" class="container__btn btn--third">Listar Tipos de documento</a>
                </div>

                <table class="table">
                    <thead class="table__head">
                        <tr class="table__row">
                            <th class="table__header">Id</th>
                            <th class="table__header">${categoria}</th>
                            <th class="table__header">Acciones</th>
                        </tr>
                    </thead>
                    <tbody class="table__body">
                        <c:if test="${empty resultados}">
                            <tr class="table__row">
                                <td class="table__cell" colspan="3">Seleccione la categoria a listar.</td>
                            </tr>
                        </c:if>
                        <c:forEach var="item" items="${resultados}">
                            <tr class="table__row">
                                <td class="table__cell" data-label="id">${item.id}</td>
                                <td class="table__cell" data-label="${categoria}">${item.nombre}</td>
                                <td class="table__cell table__cell-display" data-label="Acciones">
                                    <button class="table__btn table__btn--green btn--hover editar" type="button" data-id="${item.id}" data-nombre="${item.nombre}">Editar</button>
                                    <form action="${pageContext.request.contextPath}/sv_categorias" method="POST">
                                        <input type="hidden" name="categoria" value="${categoria}">
                                        <input type="hidden" name="id" value="${item.id}">
                                        <c:choose>
                                            <c:when test="${item.estado == true}">
                                                <button class="table__btn table__btn--red btn--hover Inhabilitar" type="button">Inhabilitar</button>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="hidden" name="action" value="habilitar">
                                                <button type="submit" class="table__btn table__btn--yellow btn--hover">Habilitar</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <form id="formEditar" class="display--none" action="${pageContext.request.contextPath}/sv_categorias" method="POST" novalidate>
                    <div class="modal__content modal__content--wd">
                        <div class="modal__header">
                            <h2>Editar ${categoria}</h2>
                            <button class="modal__close" type="button">&times;</button>
                        </div>
                        <input type="hidden" name="action" value="Edit">
                        <input type="hidden" name="categoria" value="${categoria}">
                        <input type="hidden" name="id" id="editId">
                        <div class="form__group">
                            <input type="text" name="nombre" id="editNombre" class="modal__input solo-letras" placeholder="Editar ${categoria}" maxlength="20" required>
                        </div>
                        <button class="modal__btn modal__btn--wd" type="submit">Editar</button>
                    </div>
                </form>

                <form id="formAgregar" class="display--none" action="${pageContext.request.contextPath}/sv_categorias" method="POST" novalidate>
                    <div class="modal__content modal__content--wd">
                        <div class="modal__header">
                            <h2>Agregar ${categoria}</h2>
                            <button class="modal__close" type="button">&times;</button>
                        </div>
                        <input type="hidden" name="action" value="Add">
                        <input type="hidden" name="categoria" value="${categoria}">
                        <div class="form__group">
                            <input type="text" name="nombre" class="modal__input solo-letras" placeholder="Ingrese el nombre" maxlength="20" required>
                        </div>
                        <button class="modal__btn modal__btn--wd" type="submit">Agregar</button>
                    </div>
                </form>
                <c:if test="${!empty resultados}">
                    <button id="btnAgregar" class="btn--third" type="submit">Agregar ${categoria}</button>
                </c:if>
            </section>
        </main>
    </body>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
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
    <script src="${pageContext.request.contextPath}/js/categorias.js" type="module"></script>
</html>
