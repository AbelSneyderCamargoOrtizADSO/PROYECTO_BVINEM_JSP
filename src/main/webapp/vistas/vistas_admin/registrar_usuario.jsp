<%-- 
    Document   : registrar_usuario
    Created on : 4/08/2024, 6:26:23 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Desactivar la caché del navegador, asegurando que siempre se solicite una versión nueva de la página desde el servidor
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies  
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/reg_usu.css"/>
        <title>Registrar Usuario</title>
    </head>
    <body>
        <jsp:include page="header_admin.jsp" />

        <main>
            <section class="container">
                <div id="form">
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="form form--width" novalidate>
                        <a href="${pageContext.request.contextPath}/sv_usuario" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                        <h2 class="form__title">Registrar ${tipoUser}</h2>
                        <input type="hidden" name="userId" value="${usuario.docUsu}">
                        <input type="hidden" name="action" value="${action}">
                        <input id="tipoUsuario" type="hidden" name="tipoUsuario" value="${tipoUser}">
                        <div class="form__group">
                            <label for="documento" class="form__label form__label--color">Documento de Identidad</label>
                            <input type="text" id="documento" class="form__input solo-numeros" value="${usuario.docUsu}" name="nuevoDoc" maxlength="10" required>
                        </div>
                        <div class="form__group-container">
                            <div class="form__group">
                                <label for="nombres" class="form__label form__label--color">Nombres</label>
                                <input type="text" id="nombres" class="form__input solo-letras" name="nombres" value="${usuario.nombre}" required>
                            </div>
                            <div class="form__group">
                                <label for="apellidos" class="form__label form__label--color">Apellidos</label>
                                <input type="text" id="apellidos" class="form__input solo-letras" name="apellidos" value="${usuario.apellido}" required>
                            </div>
                        </div>
                        <div class="form__group">
                            <label for="correo" class="form__label form__label--color">Correo Electrónico</label>
                            <input type="text" id="correo" class="form__input" name="correo" value="${usuario.correo}" required>
                        </div>
                        
                        <div class="form__group-container">
                            <div class="form__group">
                                <label for="contrasena" class="form__label form__label--color">Contraseña</label>
                                <input type="password" id="contrasena" class="form__input" name="password" required>
                            </div>
                            <div class="form__group">
                                <label for="contrasena" class="form__label form__label--color">Confirmar contraseña</label>
                                <input type="password" id="confirContra" class="form__input" name="confirmPass" required>
                            </div>
                        </div>
                        
                        <div class="form__group">
                            <label for="aceptarTerminos" class="form__label">
                                <input type="checkbox" id="aceptarTerminos" name="aceptarTerminos" required>
                                Acepto los <a href="${pageContext.request.contextPath}/vistas/tyc.jsp" target="_blank">términos y condiciones</a> de registro de usuario.
                            </label>
                        </div>
                        <button id="btnEnviar" type="submit" class="form__btn form__btn--bg" disabled>Guardar cambios</button>
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
            });
        </script>  
        <script src="${pageContext.request.contextPath}/js/reg_usu.js" type="module"></script>
    </body>
</html>
