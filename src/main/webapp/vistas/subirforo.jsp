<%-- 
    Document   : subirforo
    Created on : 11/07/2024, 2:14:37 p. m.
    Author     : Propietario
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
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/subirforo.css"/>
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
        <title>Crear foro</title>
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
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/subir_foro" method="POST" class="form form--wd" id="foroForm" novalidate>
                    <div class="form__group">
                        <label class="form__label" for="titulo">Título del Foro</label>
                        <input class="form__input" type="text" id="titulo" name="titulo" maxlength="50" required>
                    </div>
                    <div class="form__group form__group--flex">
                        <div class="form__group grow">
                            <select class="form__select" name="asignatura" id="asignatura" required>
                                <option value="" disabled selected>Seleccione la asignatura</option>
                                <c:forEach var="asignatura" items="${asignaturas}">
                                    <option value="${asignatura.id}">${asignatura.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="form__group grow">
                            <select class="form__select" name="idioma" id="idioma" required>
                                <option value="" disabled selected>Seleccione el idioma</option>
                                <c:forEach var="idioma" items="${idiomas}">
                                    <option value="${idioma.id}">${idioma.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>   
                        
                        <div class="form__group grow">
                            <select class="form__select" name="tipof" id="tipof" required>
                                <option value="" disabled selected>Seleccione el tipo de foro</option>
                                <c:forEach var="tipo" items="${tiposforo}">
                                    <option value="${tipo.id}">${tipo.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>    
                    </div>
                    <div class="form__group form__group--sz">
                        <label class="form__label" for="descripcion">Descripción</label>
                        <div id="editor"></div>
                        <textarea class="form__textarea" id="descripcion" name="descripcion" style="display:none;" required></textarea>
                    </div>

                    <a class="cerrar" href="${pageContext.request.contextPath}/sv_foros"><i class="bi bi-x-circle-fill"></i></a>

                    <button class="form__btn" type="submit" id="submitBtn" name="enviar">Publicar</button>
                </form>
            </div>
        </main>

        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
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
        <script src="js/subirforo.js" type="module"></script>
    </body>
</html>
