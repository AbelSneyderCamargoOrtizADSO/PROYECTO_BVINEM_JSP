<%-- 
    Document   : editar_usuarios
    Created on : 4/08/2024, 3:17:52 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/editar_usu.css"/>
        <title>Editar Usuario</title>
    </head>
    <body class="body--bg">
        <jsp:include page="header_admin.jsp" />

        <main>
            <section class="container container--margin">
                <div id="form">
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="form form__valid">
                        <a href="${pageContext.request.contextPath}/sv_usuario" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                        <input type="hidden" name="userId" value="${usuario.docUsu}">
                        <input type="hidden" id="rol" value="${usuario.rol}">
                        <input id="tipoUsuario" type="hidden" name="tipoUsuario" value="${tipoUser}">
                        <div class="form__group">
                            <label for="documento" class="form__label">Documento de Identidad</label>
                            <input type="text" id="documento" class="form__input solo-numeros obligatorio" value="${usuario.docUsu}" name="nuevoDoc">
                        </div>
                        <div class="form__group">
                            <label for="nombres" class="form__label">Nombres</label>
                            <input type="text" id="nombres" class="form__input solo-letras obligatorio" name="nombres" value="${usuario.nombre}">
                        </div>
                        <div class="form__group">
                            <label for="apellidos" class="form__label">Apellidos</label>
                            <input type="text" id="apellidos" class="form__input solo-letras obligatorio" name="apellidos" value="${usuario.apellido}">
                        </div>
                        <div class="form__group">
                            <label for="correo" class="form__label">Correo Electrónico</label>
                            <input type="text" id="correo" class="form__input obligatorio" name="correo" value="${usuario.correo}">
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
                            <span><b>Rol actual:</b> ${tipoUser}</span>
                        </div>
                        <div class="form__group">
                            <label for="cambiarRol" class="form__label">Cambiar rol</label>
                            <select class="form__select obligatorio" name="rol" id="cambiarRol">
                                <c:if test="${rol == '4'}">
                                    <option class="select__rol" value="3">Administrador</option>
                                </c:if>
                                <option class="select__rol" value="2">Docente</option>
                                <option class="select__rol" value="1">Estudiante</option>
                            </select>
                        </div>
                        <div class="form__group">
                            <span><b>Fecha de registro:</b> ${usuario.fechaRegistro}</span>
                        </div> 
                        <div class="form__group">
                            <label for="aceptarTerminos" class="form__label">
                                <input type="checkbox" id="aceptarTerminos" name="aceptarTerminos" required>
                                Acepto los <a href="${pageContext.request.contextPath}/vistas/tyc.jsp" target="_blank">términos y condiciones</a> de edición de usuario.
                            </label>
                        </div>
                        <button id="btnEnviar" type="submit" class="form__btn" disabled>Guardar cambios</button>
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
        <script src="${pageContext.request.contextPath}/js/editar_usu.js" type="module"></script>
    </body>
</html>
