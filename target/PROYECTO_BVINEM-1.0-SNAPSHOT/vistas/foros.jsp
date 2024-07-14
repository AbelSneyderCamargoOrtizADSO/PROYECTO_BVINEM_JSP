<%-- 
    Document   : foros
    Created on : 11/07/2024, 1:48:51 p. m.
    Author     : Propietario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String rol = (String) session.getAttribute("rol");
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
            <c:when test="${rol == '3'}">
                <jsp:include page="vistas_admin/header_admin.jsp" />
            </c:when>
        </c:choose>

        <main>
            <section class="filter">
                <a class="filter__btn" href="${pageContext.request.contextPath}/sv_documentos">🢀 Ir a biblioteca</a>
                <h1 class="filter__title">FOROS</h1>
                <form class="filter__filters" method="GET" action="sv_foros" id="filter__form">
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
                    <a class="foros__card" href="">
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

</html>
