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
        <link rel="stylesheet" href="css/editar_estu.css"/>
        <title>Editar datos</title>
    </head>
    <body>
        <jsp:include page="header_estu.jsp" />

        <main>
            <section class="container">
                <div class="formulario form__valid" id="form">
                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="formulario__form">
                        <a href="${pageContext.request.contextPath}/sv_documentos" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                        <input type="hidden" name="userId" value="${usuario.docUsu}">
                        <div class="formulario__grupo">
                            <label for="documento" class="formulario__label">Documento de Identidad</label>
                            <input type="text" id="documento" class="formulario__input solo-numeros obligatorio" value="${usuario.docUsu}" name="nuevoDoc">
                        </div>
                        <div class="formulario__grupo">
                            <label for="nombres" class="formulario__label">Nombres</label>
                            <input type="text" id="nombres" class="formulario__input solo-letras obligatorio" name="nombres" value="${usuario.nombre}">
                        </div>
                        <div class="formulario__grupo">
                            <label for="apellidos" class="formulario__label">Apellidos</label>
                            <input type="text" id="apellidos" class="formulario__input solo-letras obligatorio" name="apellidos" value="${usuario.apellido}">
                        </div>
                        <div class="formulario__grupo">
                            <label for="correo" class="formulario__label">Correo Electrónico</label>
                            <input type="email" id="correo" class="formulario__input obligatorio" name="correo" value="${usuario.correo}">
                        </div>
                        <div class="formulario__grupo">
                            <label for="contrasena" class="formulario__label">Cambiar contraseña</label>
                            <input type="password" id="contrasena" class="formulario__input" name="password">
                        </div>
                        <div class="formulario__grupo">
                            <span><b>Fecha de registro:</b> ${usuario.fechaRegistro}</span>
                        </div>
                        <button type="submit" class="formulario__boton" name="estudianteEdit">Guardar cambios</button>
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
        <script src="js/editar_estu.js"></script>
        <script src="js/validaciones.js"></script>
    </body>
</html>
