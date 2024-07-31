<%-- 
    Document   : busqueda
    Created on : 30/07/2024, 8:17:29 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/foros.css">
        <link rel="stylesheet" href="css/home.css">
        <title>Resultados de Búsqueda</title>
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
            <section class="container">
                <div class="filter" id="filter__form">
                    <a href="${pageContext.request.contextPath}/sv_documentos" class="filter__btn margin-right">🢀</a>
                    <h1 class="filter__title">Resultados de Búsqueda</h1>
                </div>
                <c:if test="${tipo == 'libros'}">
                    <c:if test="${not empty resultadosLibros}">
                        <section class="books">
                            <c:forEach var="documento" items="${resultadosLibros}">
                                <div class="books__book">
                                    <a class="books__content" href="mostrar_pdf?id=${documento.id}">
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
                                            <button type="button" class="eliminarDoc"><img src="assets/papelera.png" alt="Eliminar Foro"/></button>
                                        </form>   
                                    </c:if>
                                </div>
                            </c:forEach>
                        </section>
                    </c:if>
                    <c:if test="${empty resultadosLibros}">
                        <p class="empty">No se encontraron resultados para la búsqueda.</p>
                    </c:if>
                </c:if>

                <c:if test="${tipo == 'foros'}">
                    <c:if test="${not empty resultadosForos}">
                        <section class="foros">
                            <c:forEach var="foro" items="${resultadosForos}">
                                <a class="foros__card" href="mostrar_foro?id=${foro.id}">
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
                    </c:if>
                    <c:if test="${empty resultadosForos}">
                        <p class="empty">No se encontraron resultados para la búsqueda.</p>
                    </c:if>
                </c:if>
            </section>
        </main>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="js/home.js"></script>
    </body>
</html>
