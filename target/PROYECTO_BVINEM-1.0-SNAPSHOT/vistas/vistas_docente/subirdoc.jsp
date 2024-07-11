<%-- 
    Document   : subirdoc
    Created on : 3/07/2024, 9:43:13 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/subirdoc.css">
        <title>Cargar</title>
    </head>

    <body>
        <header class="header">
            <img src="${pageContext.request.contextPath}/assets/escu.png" alt="" class="header__img">
            <h1 class="header__title">Subir Documento</h1>
            <div class="header__user">
                <img src="${pageContext.request.contextPath}/assets/iconouser.png" alt="" class="header__img header__img--display">
                <form action="${pageContext.request.contextPath}/cerrar_sesion" method="get">
                    <button type="submit" class="header__btn">Cerrar sesión</button>
                    <button type="submit" class="header__btn header__btn--resp"><i class="bi bi-box-arrow-right"></i></button>
                </form>
            </div>
        </header>

        <main>
            <section class="container">
                <form action="${pageContext.request.contextPath}/subir_doc" method="POST" class="container__form" enctype="multipart/form-data">
                    <div class="container__inputs">
                        <input type="text" class="container__input" placeholder="Título del documento" name="titulo">
                        <input type="text" class="container__input" placeholder="Autor del documento" name="autor">
                        <input type="text" class="container__input" placeholder="Descripción del documento" name="descripcion">
                        <input type="text" class="container__input" placeholder="Año de publicación" name="año">

                        <select class="container__select" name="asignatura">
                            <c:forEach var="asignatura" items="${asignaturas}">
                                <option value="${asignatura.id}">${asignatura.nombre}</option>
                            </c:forEach>
                        </select>

                        <select class="container__select" name="idioma">
                            <c:forEach var="idioma" items="${idiomas}">
                                <option value="${idioma.id}">${idioma.nombre}</option>
                            </c:forEach>
                        </select>

                        <select class="container__select" name="tipo">
                            <c:forEach var="tipo" items="${tipos}">
                                <option value="${tipo.id}">${tipo.nombre}</option>
                            </c:forEach>
                        </select>

                        <div>
                            <label><b>Cargar miniatura o portada del libro:</b></label>
                            <input type="file" class="container__input" name="miniatura"  accept="image/*">
                        </div>

                        <div>
                            <label><b>Cargar Libro o documento en PDF:</b></label>
                            <input type="file" class="container__input" name="documentoPDF" accept="application/pdf">
                        </div>

                        <a class="cerrar" href="${pageContext.request.contextPath}/sv_documentos"><i class="bi bi-x-circle-fill"></i></a>
                    </div>

                    <button type="submit" class="container__btn" name="submit">PUBLICAR AHORA ➜</button>
                </form>

                <div class="container__prev">
                    <h2 class="container__title">Previsualización de miniaturas</h2>
                    <img src="${pageContext.request.contextPath}/assets/libro.png" alt="" class="container__img">
                </div>

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
            });
        </script>

    </body>

</html>