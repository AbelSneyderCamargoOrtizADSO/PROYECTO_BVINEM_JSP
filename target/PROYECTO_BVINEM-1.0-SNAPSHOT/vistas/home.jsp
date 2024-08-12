<%-- 
    Document   : home
    Created on : 27/06/2024, 2:15:08 p. m.
    Author     : Propietario
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
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/home.css">
        <title>BIBLIOTECA</title>
    </head>

    <body>
        <c:choose>
            <c:when test="${rol == '1'}">
                <jsp:include page="vistas_estudiante/header_estu.jsp" />
            </c:when>
            <c:when test="${rol == '2'}">
                <jsp:include page="vistas_docente/header_docen.jsp" />
            </c:when>
            <c:when test="${rol == '3' || rol == '4'}">
                <jsp:include page="vistas_admin/header_admin.jsp" />
            </c:when>
        </c:choose>

        <main>
            <section class="filter">
                <form class="filter__filters" method="GET" action="sv_documentos" id="filter__form">
                    <select class="filter__select" name="asignatura">
                        <option value="">Asignatura</option>
                        <c:forEach var="asignatura" items="${asignaturas}">
                            <option value="${asignatura.id}">${asignatura.nombre}</option>
                        </c:forEach>
                    </select>

                    <select class="filter__select" name="idioma">
                        <option value="">Idioma</option>
                        <c:forEach var="idioma" items="${idiomas}">
                            <option value="${idioma.id}">${idioma.nombre}</option>
                        </c:forEach>
                    </select>

                    <select class="filter__select" name="tipo">
                        <option value="">Tipo</option>
                        <c:forEach var="tipo" items="${tipos}">
                            <option value="${tipo.id}">${tipo.nombre}</option>
                        </c:forEach>
                    </select>
                    <button class="filter__btn filter__btn-bg" type="submit">Filtrar</button>
                </form>

                <a class="filter__btn" href="${pageContext.request.contextPath}/sv_foros">Ir a foros ➜</a>
            </section>

            <section class="books">
                <c:forEach var="documento" items="${documentos}">
                    <div class="books__book" data-id="${documento.id}" data-titulo="${documento.titulo}"  data-autor="${documento.autor}"  data-descripcion="${documento.descripcion}"  data-year="${documento.year}">
                        <a class="books__content" href="${documento.archivoPDF}">
                            <img src="${documento.miniaturaPath}" alt="" class="books__img">
                            <div class="books__info">
                                <span class="books__title">${documento.titulo}</span>
                                <p class="books__autor">${documento.autor}<span class="books__year">${documento.year}</span> </p>
                            </div>
                            <div class="books__details">
                                <p class="books__description">${documento.descripcion}</p>
                                <p class="books__type"><b>Tipo:</b> ${documento.tipo}</p>
                                <p class="books__subject"><b>Asignatura:</b> ${documento.asignatura}</p>
                                <span class="books__language">${documento.idioma}</span>
                            </div>
                        </a>
                        <c:if test="${documento.userDoc == sessionScope.UserDoc || (rol == '3' || rol == '4')}">
                            <form action="${pageContext.request.contextPath}/sv_documentos" method="POST" class="book__eliminar">
                                <input type="hidden" name="docId" value="${documento.id}">
                                <input type="hidden" name="rutaMiniatura" value="${documento.miniaturaPath}">
                                <input type="hidden" name="rutaPDF" value="${documento.archivoPDF}">
                                <button type="button" class="eliminarDoc"><img src="assets/papelera.png" alt="Eliminar Foro"/></button>
                            </form>
                        </c:if>
                        <c:if test="${documento.userDoc == sessionScope.UserDoc}">
                            <button type="button" class="editDoc" data-documento='${documento}'><img src="assets/editar.png" alt="Editar Documento"/></button>
                        </c:if>
                    </div>
                </c:forEach>
            </section>
            
            <div id="editModal" class="display--none">
                <div class="modal-content">
                    <span class="close" id="closeModal">&times;</span>
                    <form id="editForm" method="POST" action="sv_documentos" class="form" novalidate>
                        <h1 class="form__title">Editar documento</h1>
                        <input type="hidden" name="docId" id="editId">
                        <div class="form__group">
                            <label for="editTitulo">Título:</label>
                            <input type="text" name="titulo" id="editTitulo" class="form__input" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <label for="editAutor">Autor:</label>
                            <input type="text" name="autor" id="editAutor" class="form__input solo-letras" maxlength="35" required>
                        </div>
                        <div class="form__group">
                            <label for="editDescripcion">Descripción:</label>
                            <textarea name="descripcion" id="editDescripcion" class="form__input" rows="4" maxlength="320" required></textarea>
                        </div>
                        <div class="form__group">
                            <label for="editYear">Año de Publicación:</label>
                            <input type="text" name="year" id="editYear" class="form__input solo-numeros" maxlength="5" required>
                        </div>
                        <button type="submit" name="editDocumento" class="form__btn filter__btn filter__btn-bg">Guardar Cambios</button>
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
            // PAGINACIONES
        </script>
        <script src="js/home.js" type="module"></script>
    </body>

</html>