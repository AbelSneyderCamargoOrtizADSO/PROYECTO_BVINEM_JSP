<%-- 
    Document   : foros
    Created on : 11/07/2024, 1:48:51 p. m.
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/foros.css">
        <title>FOROS</title>
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
                <button class="filter__toggle filter__btn-bg" id="filterToggle">Mostrar Filtros</button>
                <a class="btn__redirect btn__redirect--bg" href="${pageContext.request.contextPath}/sv_documentos">🢀 Ir a biblioteca</a>
                <h1 class="filter__title">FOROS</h1>
                <form class="filter__filters filter__filters--bg" method="GET" action="sv_foros" id="filter__form">
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

                    <select class="filter__select" name="tipof" id="tipof">
                        <option value="">Tipo</option>
                        <c:forEach var="tipo" items="${tiposforo}">
                            <option value="${tipo.id}">${tipo.nombre}</option>
                        </c:forEach>
                    </select>

                    <button class="filter__btn filter__btn-bg" type="submit">Filtrar</button>
                </form>
            </section>

            <section class="foros">
                <c:forEach var="foro" items="${foros}">
                    <a class="card" href="mostrar_foro?id=${foro.id}">
                        <div class="card__info">
                            <img src="assets/foro.jpg" alt="" class="card__icon">
                            <p class="card__title">${foro.titulo} <br> <span class="card__fecha">${foro.fecha}</span></p>
                            <span class="card__tipo">${foro.tipo}</span>
                        </div>
                        <p class="card__text">${foro.descripcionResumida}</p>
                        <p class="card__tags">${foro.asignatura} - ${foro.idioma}</p>
                    </a>
                </c:forEach>
            </section>
        </main>
    </body>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        // Mostrar SweetAlert si hay un mensaje de error en la sesión
        window.addEventListener('load', function () {
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
    <script src="js/foros.js" type="module"></script>
</html>
