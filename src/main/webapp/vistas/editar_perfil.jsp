<%-- 
    Document   : editar_estudiante
    Created on : 27/07/2024, 3:29:00 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="form form__valid">
                        <a href="${pageContext.request.contextPath}/sv_documentos" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                        <input type="hidden" name="userId" value="${usuario.docUsu}">
                        <input id="tipoUsuario" type="hidden" name="tipoUsuario" value="${rol}">
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
                            <span><b>Fecha de registro:</b> ${usuario.fechaRegistro}</span>
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
