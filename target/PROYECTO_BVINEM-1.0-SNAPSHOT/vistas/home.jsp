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

    String rol = (String) session.getAttribute("rol");
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
            <c:when test="${rol == '3'}">
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
                    <button class="header__btn" type="submit">Filtrar</button>
                </form>

                <a class="filter__btn" href="${pageContext.request.contextPath}/sv_foros">Ir a foros ➜</a>
            </section>

            <section class="books">
                <c:forEach var="documento" items="${documentos}">
                    <a class="books__book" href="mostrar_pdf?id=${documento.id}">
                        <img src="assets/libro2.png" alt="" class="books__img">
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
                </c:forEach>
            </section>

        </main>

        <!-- EVITAR ENVIO DE FORMULARIO CON FILTROS VACIOS -->
        <script>
            document.getElementById('filter__form').addEventListener('submit', function (event) {
                // Obtener todos los select del formulario
                let selects = this.querySelectorAll('select'); // Aquí, this hace referencia al elemento al que se adjunto el evento addEventListener (es decir el formulario) 

                // Iterar sobre los select y eliminar aquellos que están vacíos
                selects.forEach(select => {
                    if (!select.value) {
                        select.name = '';  // Eliminar el nombre del campo para que no sea enviado
                    }
                });
            });
        </script>
    </body>

</html>