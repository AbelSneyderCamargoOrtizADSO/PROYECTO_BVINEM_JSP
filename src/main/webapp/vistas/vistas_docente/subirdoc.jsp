<%-- 
    Document   : subirdoc
    Created on : 3/07/2024, 9:43:13 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Verifica si la sesión es nula o si no existe un atributo "logueado" en la sesión.
    if (session == null || session.getAttribute("logueado") == null) {
        // Si la sesión no existe o el usuario no está logueado, redirige al usuario a "index.jsp".
        // `request.getContextPath()` asegura que la redirección sea relativa a la raíz de la aplicación web.
        response.sendRedirect(request.getContextPath() + "/index.jsp");

        // Detiene la ejecución del resto del código para evitar que se procese la solicitud después de la redirección.
        return;
    }

    // Desactiva la caché del navegador para esta respuesta, asegurando que siempre se solicite una nueva versión de la página desde el servidor.
    // Esto evita que el navegador almacene en caché esta página y siempre obtenga una versión actualizada del servidor.
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1

    // Asegura compatibilidad con navegadores más antiguos estableciendo el encabezado "Pragma" a "no-cache".
    // Este encabezado es parte del protocolo HTTP 1.0 y se usa para desactivar la caché.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0

    // Establece la fecha de expiración de la página a 0, lo que indica a los proxies y cachés intermedios que la página ya ha expirado
    // y que no deben servirla desde su caché. Esto fuerza a los clientes a solicitar una nueva copia desde el servidor.
    response.setDateHeader("Expires", 0); // Proxies

%>
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
                <form action="${pageContext.request.contextPath}/subir_doc" method="POST" class="form form--pd" enctype="multipart/form-data" novalidate>
                        <div class="form__group">
                            <input type="text" id="titulo" class="form__input form__input--bg" placeholder="Título del documento" name="titulo" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <input type="text" id="autor" class="form__input form__input--bg solo-letras" placeholder="Autor del documento" name="autor" maxlength="35" required>
                        </div>
                        <div class="form__group">
                            <textarea id="descrip" class="form__input form__input--bg" placeholder="Descripción del documento" name="descripcion" rows="4" maxlength="320" required></textarea>
                        </div>
                        <div class="form__group">
                            <input id="year" type="text" class="form__input form__input--bg solo-numeros" placeholder="Año de publicación" name="año" maxlength="5" required>
                        </div>

                        <div class="form__group">
                            <select id="asignatura" class="form__select form__input--bg" name="asignatura" required>
                                <option value="" disabled selected>Seleccione la asignatura</option>
                                <c:forEach var="asignatura" items="${asignaturas}">
                                    <option value="${asignatura.id}">${asignatura.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    
                        <div class="form__group">        
                            <select id="idioma" class="form__select form__input--bg" name="idioma" required>
                                <option value="" disabled selected>Seleccione el idioma</option>
                                <c:forEach var="idioma" items="${idiomas}">
                                    <option value="${idioma.id}">${idioma.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    
                        <div class="form__group">
                            <select id="tipod" class="form__select form__input--bg" name="tipo" required>
                                <option value="" disabled selected>Seleccione el tipo de documento</option>
                                <c:forEach var="tipo" items="${tipos}">
                                    <option value="${tipo.id}">${tipo.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    

                        <div class="form__group">
                            <label><b>Cargar miniatura o portada del libro</b></label>
                            <input id="inputImagen" type="file" class="form__input form__input--bg" name="miniatura" accept=".jpg, .jpeg, .png" required>
                        </div>

                        <div class="form__group">
                            <label><b>Cargar Libro o documento en PDF</b></label>
                            <input id="inputPdf" type="file" class="form__input form__input--bg" name="documentoPDF" accept="application/pdf" required>
                        </div>

                        <a class="form__cerrar" href="${pageContext.request.contextPath}/sv_documentos"><i class="bi bi-x-circle-fill"></i></a>

                    <button type="submit" class="form__btn" name="subirDoc">PUBLICAR AHORA ➜</button>
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
        <script src="js/subirdoc.js" type="module"></script>

    </body>

</html>