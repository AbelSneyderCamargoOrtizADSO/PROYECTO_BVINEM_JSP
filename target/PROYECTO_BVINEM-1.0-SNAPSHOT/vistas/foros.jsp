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
                <div class="filter__filters">
                    <select name="" id="" class="filter__select">
                        <option value="">Asignatura</option>
                    </select>
                    <select name="" id="" class="filter__select">
                        <option value="">Idioma</option>
                    </select>
                    <select name="" id="" class="filter__select">
                        <option value="">Tipo</option>
                    </select>
                </div>
            </section>

            <section class="foros">
                <a class="foros__card" href="">
                    <div class="card__info">
                        <img src="assets/foro.jpg" alt="" class="card__icon">
                        <p class="card__title">Lectura del Mes: Explora, Comparte y Analiza <br> <span class="card__fecha">24 de enero
                                de 2024</span></p>

                        <span class="card__tipo">Discusion</span>
                    </div>
                    <p class="card__text">¡Bienvenidos al Foro de Lectura del Mes! Cada mes, seleccionaremos un libro emocionante y
                        educativo para que los
                        estudiantes del INEM lo lean y discutan. Aquí, podrás compartir tus pensamientos, opiniones y análisis sobre
                        el libro
                        seleccionado. ¿Qué te pareció la historia? ¿Qué temas te llamaron la atención? ¿Hubo personajes que te
                        identificaste o
                        te sorprendieron? Únete a la conversación y forma parte de esta comunidad de lectura.</p>
                </a>
            </section>
        </main>
    </body>

</html>
