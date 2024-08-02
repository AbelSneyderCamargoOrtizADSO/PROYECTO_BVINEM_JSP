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
        <c:choose>
            <c:when test="${rol == '2'}">
                <jsp:include page="header_docen.jsp" />
            </c:when>
            <c:when test="${rol == '4'}">
                <jsp:include page="../vistas_admin/header_admin.jsp" />
            </c:when>
        </c:choose>

        <main>
            <section class="container">
                <form action="${pageContext.request.contextPath}/subir_doc" method="POST" class="container__form form__valid" enctype="multipart/form-data">
                    <div class="container__inputs">
                        <div class="form__group">
                            <input type="text" class="container__input obligatorio" placeholder="Título del documento" name="titulo" maxlength="40">
                        </div>
                        <div class="form__group">
                            <input type="text" class="container__input obligatorio solo-letras" placeholder="Autor del documento" name="autor" maxlength="35">
                        </div>
                        <div class="form__group">
                            <textarea class="container__input container__input-textarea obligatorio" placeholder="Descripción del documento" name="descripcion" rows="4" maxlength="320"></textarea>
                        </div>
                        <div class="form__group">
                            <input type="text" class="container__input obligatorio solo-numeros" placeholder="Año de publicación" name="año" maxlength="5">
                        </div>

                        <div class="form__group">
                            <select class="container__select obligatorio" name="asignatura">
                                <option value="" disabled selected>Seleccione la asignatura</option>
                                <c:forEach var="asignatura" items="${asignaturas}">
                                    <option value="${asignatura.id}">${asignatura.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    
                        <div class="form__group">        
                            <select class="container__select obligatorio" name="idioma">
                                <option value="" disabled selected>Seleccione el idioma</option>
                                <c:forEach var="idioma" items="${idiomas}">
                                    <option value="${idioma.id}">${idioma.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    
                        <div class="form__group">
                            <select class="container__select obligatorio" name="tipo">
                                <option value="" disabled selected>Seleccione el tipo de documento</option>
                                <c:forEach var="tipo" items="${tipos}">
                                    <option value="${tipo.id}">${tipo.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    

                        <div class="form__group">
                            <label><b>Cargar miniatura o portada del libro</b></label>
                            <input id="inputImagen" type="file" class="container__input obligatorio" name="miniatura" accept=".jpg, .jpeg, .png">
                        </div>

                        <div class="form__group">
                            <label><b>Cargar Libro o documento en PDF</b></label>
                            <input id="inputPdf" type="file" class="container__input obligatorio" name="documentoPDF" accept="application/pdf">
                        </div>

                        <a class="cerrar" href="${pageContext.request.contextPath}/sv_documentos"><i class="bi bi-x-circle-fill"></i></a>
                    </div>

                    <button type="submit" class="container__btn" name="subirDoc">PUBLICAR AHORA ➜</button>
                </form>

                <div class="container__prev">
                    <h2 class="container__title">Previsualización de miniatura</h2>
                    <img alt="" class="container__img" id="previsualizacionMiniatura">
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
        <script src="js/validaciones.js" type="module"></script>
        <script src="js/subirdoc.js" type="module"></script>

    </body>

</html>