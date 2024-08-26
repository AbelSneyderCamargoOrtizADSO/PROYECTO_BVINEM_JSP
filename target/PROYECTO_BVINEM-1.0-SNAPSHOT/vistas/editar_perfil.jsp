<%-- 
    Document   : editar_estudiante
    Created on : 27/07/2024, 3:29:00 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/editar_usu.css"/>
        <title>Editar datos</title>
    </head>
    <body id="body">
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
                <div id="form">
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="form" novalidate>
                        <a href="${pageContext.request.contextPath}/sv_documentos" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                        <input type="hidden" name="userId" value="${usuario.docUsu}">
                        <input id="tipoUsuario" type="hidden" name="tipoUsuario" value="${rol}">
                        <input id="rolUsu" type="hidden" name="rol" value="${rol}">
                        <input type="hidden" name="tipoEdicion" value="misDatos">
                        <div class="form__group">
                            <label for="documento" class="form__label">Documento de Identidad</label>
                            <input type="text" id="documento" class="form__input solo-numeros" value="${usuario.docUsu}" name="nuevoDoc" maxlength="10" required>
                        </div>
                        <div class="form__group">
                            <label for="nombres" class="form__label">Nombres</label>
                            <input type="text" id="nombres" class="form__input solo-letras" name="nombres" value="${usuario.nombre}" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <label for="apellidos" class="form__label">Apellidos</label>
                            <input type="text" id="apellidos" class="form__input solo-letras" name="apellidos" value="${usuario.apellido}" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <label for="correo" class="form__label">Correo Electrónico</label>
                            <input type="text" id="correo" class="form__input" name="correo" value="${usuario.correo}" required>
                        </div>
                        <div class="form__group">
                            <label for="contrasena" class="form__label">Cambiar contraseña</label>
                            <input type="password" id="contrasena" class="form__input" name="password">
                        </div>
                        <div class="form__group">
                            <label for="contrasena" class="form__label">Confirmar contraseña</label>
                            <input type="password" id="confirContra" class="form__input" name="confirmPass">
                        </div>
                        <div class="form__group">
                            <span><b>Fecha de registro:</b> ${usuario.fechaRegistro}</span>
                        </div>
                        <div class="form__group">
                            <label for="aceptarTerminos" class="form__label">
                                <input type="checkbox" id="aceptarTerminos" name="aceptarTerminos" required>
                                Acepto los <a href="${pageContext.request.contextPath}/vistas/tyc.jsp" target="_blank">términos y condiciones</a> de edición de perfil.
                            </label>
                        </div>
                        <button id="btnEnviar" type="submit" class="form__btn">Guardar cambios</button>
                    </form>
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
        <script src="js/editar_perfil.js" type="module"></script>
    </body>
</html>
